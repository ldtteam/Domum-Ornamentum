package com.ldtteam.domumornamentum.client.model;

import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.retexturing.RetexturedModelPart;
import com.ldtteam.domumornamentum.client.model.retexturing.TargetTextureSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import com.ldtteam.domumornamentum.client.model.utils.SingleBlockBlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.quad.MutableQuad;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Port of the old 1.21.1 {@code ModelSpriteQuadTransformer} pipeline to the 26.1 rendering stack.
 *
 * <p>Each quad of our own model is remapped so that its texture coordinates, normalized within the sprite
 * it currently uses, cover the whole sprite of the block state the slot mimics (see {@link #retextureQuad}).
 * The quad's {@code MaterialInfo.tintIndex} is rewritten to an index into this instance's dynamic tint list;
 * see {@link com.ldtteam.domumornamentum.client.event.handlers.MateriallyTexturedBlockTintProvider}, which is
 * the 26.1 replacement for the old {@code getColorFor}/{@code BlockColor} machinery (quad colors are now
 * resolved per block instance through {@code IClientBlockExtensions#collectDynamicTintValues}).
 */
public final class RetexturingHandler {

    private static final ThreadLocal<RandomSource> RANDOM = ThreadLocal.withInitial(() -> RandomSource.createThreadLocalInstance(42L));
    /** Safety valve for the target cache; mappings are cheap but unbounded in theory. */
    private static final int MAX_CACHED_MAPPINGS = 512;

    /** Resolved targets per texture mapping. Cached because resolving requires scanning the target block's model. */
    private static final Map<MaterialTextureData, ResolvedTargets> TARGET_CACHE = new ConcurrentHashMap<>();

    /** Clears all cached retexturing state (on resource reload). */
    public static void clearCache() {
        TARGET_CACHE.clear();
    }

    /**
     * @return the ordered list of targets for a texture mapping. Index {@code i} is exactly the tint index used on quads
     *         whose slot resolves to this target, so color providers can fill an ARGB list one-to-one with it.
     */
    public static List<TargetTextureSource> targetsFor(final MaterialTextureData textureData) {
        return resolvedTargets(textureData).targets();
    }

    /**
     * Retextures all parts of the parent model according to the given texture mapping.
     * One input part yields at most one output part (empty ones are dropped, e.g. fully erased blocks).
     */
    @SuppressWarnings("deprecation")
    public static List<BlockStateModelPart> retexturedParts(
            final List<BlockStateModelPart> parentParts,
            final MaterialTextureData textureData,
            final BlockPos pos,
            final BlockAndTintGetter level) {

        if (textureData.isEmpty()) {
            return parentParts; //nothing to do
        }

        final ResolvedTargets resolved = resolvedTargets(textureData);
        final List<BlockStateModelPart> result = new ArrayList<>(parentParts.size());

        for (final BlockStateModelPart part : parentParts) {
            final QuadCollection.Builder builder = new QuadCollection.Builder();
            boolean any = false;

            //Culled quads, grouped per direction.
            for (final Direction direction : Direction.values()) {
                for (final BakedQuad quad : part.getQuads(direction)) {
                    final BakedQuad retextured = processQuad(quad, textureData, resolved);
                    if (retextured != null) {
                        builder.addCulledFace(direction, retextured);
                        any = true;
                    }
                }
            }

            //Unculled quads.
            for (final BakedQuad quad : part.getQuads(null)) {
                final BakedQuad retextured = processQuad(quad, textureData, resolved);
                if (retextured != null) {
                    builder.addUnculledFace(retextured);
                    any = true;
                }
            }

            if (!any) {
                continue;
            }

            final QuadCollection quads = builder.build();
            result.add(new RetexturedModelPart(
                    quads, part.useAmbientOcclusion(), part.particleMaterial(), quads.materialFlags()));
        }

        return result;
    }

    /**
     * @return the retextured quad, {@code quad} unchanged if it is not a re-texturable slot (or has no matching target texture),
     *         or {@code null} if the slot was explicitly erased.
     */
    private static BakedQuad processQuad(
            final BakedQuad quad,
            final MaterialTextureData textureData,
            final ResolvedTargets resolved) {

        final Identifier textureName = spriteName(quad);

        //Not one of our re-texturable slots; keep as-is.
        if (!textureData.components().containsKey(textureName)) {
            return quad;
        }

        //This is an erasure statement: no quad with that texture should be emitted into the resulting model.
        final Block targetBlock = textureData.components().get(textureName);
        if (targetBlock == null) {
            return null;
        }

        final int materialIndex = resolved.indices().getOrDefault(textureName, -1);
        final TargetTextureSource source = materialIndex >= 0 ? resolved.targets().get(materialIndex) : null;

        //No matching texture in the target model; keep the original quad rather than creating a hole.
        if (source == null || source.sourceQuad() == null) {
            return quad;
        }

        return retextureQuad(quad, source.sourceQuad(), materialIndex);
    }

    /**
     * 26.1 port of the old {@code ModelSpriteQuadTransformer} quad logic:
     * normalize each vertex UV within the shape quad's sprite bounds and stretch it to cover the whole target sprite.
     */
    public static BakedQuad retextureQuad(final BakedQuad shape, final BakedQuad textureSource, final int materialIndex) {

        final TextureAtlasSprite sourceSprite = shape.materialInfo().sprite();
        final TextureAtlasSprite targetSprite = textureSource.materialInfo().sprite();

        final float minU = sourceSprite.getU0();
        final float uDelta = sourceSprite.getU1() - minU;
        final float minV = sourceSprite.getV0();
        final float vDelta = sourceSprite.getV1() - minV;

        final MutableQuad quad = new MutableQuad().setFrom(shape);
        for (int vertexIndex = 0; vertexIndex < BakedQuad.VERTEX_COUNT; vertexIndex++) {
            //Normalize within the shape sprite's atlas bounds, then map onto the full target sprite.
            final float u = uDelta > 0 ? Mth.clamp((quad.u(vertexIndex) - minU) / uDelta, 0f, 1f) : 0.5f;
            final float v = vDelta > 0 ? Mth.clamp((quad.v(vertexIndex) - minV) / vDelta, 0f, 1f) : 0.5f;

            quad.setUv(vertexIndex, targetSprite.getU(u), targetSprite.getV(v));
        }

        //Adopt the target's sprite and render attributes so cutout/translucent targets render correctly.
        final BakedQuad.MaterialInfo sourceInfo = textureSource.materialInfo();
        quad.setSprite(sourceInfo.sprite(), sourceInfo.layer(), sourceInfo.itemRenderType());

        //Tint: index into this block instance's dynamic tint list (see MateriallyTexturedBlockTintProvider).
        quad.setTintIndex(materialIndex);

        return quad.toBakedQuad();
    }

    private static ResolvedTargets resolvedTargets(final MaterialTextureData textureData) {
        if (TARGET_CACHE.size() >= MAX_CACHED_MAPPINGS) {
            TARGET_CACHE.clear();
        }
        return TARGET_CACHE.computeIfAbsent(textureData, RetexturingHandler::resolve);
    }

    /**
     * Resolves every re-texturable slot of the mapping to its target. Slots are processed in sorted name order so that
     * value-equal mappings (regardless of insertion order) produce identical tint indices.
     */
    private static ResolvedTargets resolve(final MaterialTextureData textureData) {

        final List<Identifier> names = new ArrayList<>(textureData.components().keySet());
        names.sort(Comparator.naturalOrder());

        final List<TargetTextureSource> targets = new ArrayList<>(names.size());
        final Map<Identifier, Integer> indices = new HashMap<>();

        for (final Identifier name : names) {
            //Null components are erasures; they get no tint entry and drop their quads in processQuad.
            if (!textureData.components().containsKey(name) || textureData.components().get(name) == null) {
                continue;
            }

            targets.add(resolveTarget(textureData.components().get(name), name));
            indices.put(name, targets.size() - 1);
        }

        return new ResolvedTargets(List.copyOf(targets), Map.copyOf(indices));
    }

    /** Finds the first quad of the target block's model using exactly this texture; its sprite and tint index become the retexture source. */
    private static TargetTextureSource resolveTarget(final Block block, final Identifier textureName) {

        final BlockState state = block.defaultBlockState();

        BakedQuad sourceQuad = null;

        //Simulated single-block environment: the target model is queried as if it stood at the retextured position.
        final SingleBlockBlockAndTintGetter getter = new SingleBlockBlockAndTintGetter.Builder()
                .withBlockState(state)
                .withPos(BlockPos.ZERO)
                .createSingleBlockBlockAndTintGetter();

        final RandomSource random = RANDOM.get();
        random.setSeed(state.getSeed(BlockPos.ZERO));

        final List<BlockStateModelPart> parts = new ArrayList<>();
        Minecraft.getInstance().getModelManager()
                .getBlockStateModelSet()
                .get(state)
                .collectParts(getter, BlockPos.ZERO, state, random, parts);

        outer:
        for (final BlockStateModelPart part : parts) {
            //Deterministic scan order so repeated resolutions find the same quad.
            for (final Direction direction : Direction.values()) {
                for (final BakedQuad quad : part.getQuads(direction)) {
                    sourceQuad = quad;
                    break outer;
                }
            }

            for (final BakedQuad quad : part.getQuads(null)) {
                sourceQuad = quad;
                break outer;
            }
        }

        return sourceQuad == null ? TargetTextureSource.missing(state)
                : new TargetTextureSource(state, sourceQuad.materialInfo().tintIndex(), sourceQuad);
    }

    private static Identifier spriteName(final BakedQuad quad) {
        return quad.materialInfo().sprite().contents().name();
    }

    /** Immutable result of resolving a texture mapping: ordered targets plus slot name to tint index. */
    private record ResolvedTargets(List<TargetTextureSource> targets, Map<Identifier, Integer> indices) {}
}
