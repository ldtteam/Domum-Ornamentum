package com.ldtteam.domumornamentum.client.model.item;

import com.google.common.base.Suppliers;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.quad.MutableQuad;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * Item-model counterpart to the materialized block model.
 *
 * <p>26.2 no longer exposes the legacy {@code BakedModel#getOverrides()} path
 * used by Domum Ornamentum to retexture item stacks. This model keeps the
 * normal baked geometry and remaps only the sprites named by the stack's
 * {@link MaterialTextureData}. UVs, normals, colours and transforms remain
 * intact through NeoForge's {@link MutableQuad}.</p>
 */
public final class MateriallyTexturedItemModel implements ItemModel
{
    private final List<ItemTintSource> tints;
    private final QuadCollection baseQuads;
    private final Supplier<Vector3fc[]> extents;
    private final ModelRenderProperties properties;
    private final Matrix4fc transformation;
    private final ConcurrentMap<MaterialTextureData, QuadCollection> retexturedQuads = new ConcurrentHashMap<>();
    private final ConcurrentMap<Block, TargetTextures> targetTextures = new ConcurrentHashMap<>();

    private MateriallyTexturedItemModel(
        final List<ItemTintSource> tints,
        final QuadCollection baseQuads,
        final ModelRenderProperties properties,
        final Matrix4fc transformation
    )
    {
        this.tints = tints;
        this.baseQuads = baseQuads;
        this.properties = properties;
        this.transformation = transformation;
        this.extents = Suppliers.memoize(() -> computeExtents(baseQuads.getAll()));
    }

    private static Vector3fc[] computeExtents(final List<BakedQuad> quads)
    {
        final Set<Vector3fc> result = new HashSet<>();
        for (final BakedQuad quad : quads)
        {
            for (int vertex = 0; vertex < BakedQuad.VERTEX_COUNT; vertex++)
            {
                result.add(quad.position(vertex));
            }
        }
        return result.toArray(Vector3fc[]::new);
    }

    @Override
    public void update(
        final ItemStackRenderState output,
        final ItemStack item,
        final ItemModelResolver resolver,
        final ItemDisplayContext displayContext,
        @Nullable final ClientLevel level,
        @Nullable final ItemOwner owner,
        final int seed
    )
    {
        output.appendModelIdentityElement(this);
        final ItemStackRenderState.LayerRenderState layer = output.newLayer();

        if (item.hasFoil())
        {
            final ItemStackRenderState.FoilType foilType = hasSpecialAnimatedTexture(item)
                ? ItemStackRenderState.FoilType.SPECIAL
                : ItemStackRenderState.FoilType.STANDARD;
            layer.setFoilType(foilType);
            output.setAnimated();
            output.appendModelIdentityElement(foilType);
        }

        if (!this.tints.isEmpty())
        {
            final IntList tintLayers = layer.tintLayers();
            for (final ItemTintSource tintSource : this.tints)
            {
                final int tint = tintSource.calculate(item, level, owner == null ? null : owner.asLivingEntity());
                tintLayers.add(tint);
                output.appendModelIdentityElement(tint);
            }
        }

        MaterialTextureData textureData = MaterialTextureData.readFromItemStack(item);
        if (textureData.isEmpty())
        {
            textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(item);
        }

        final QuadCollection quads = textureData.isEmpty()
            ? this.baseQuads
            : this.retexturedQuads.computeIfAbsent(textureData, this::buildRetexturedQuads);

        layer.setExtents(this.extents);
        layer.setLocalTransform(this.transformation);
        this.properties.applyToLayer(layer, displayContext);
        if (!textureData.isEmpty())
        {
            remapParticleMaterial(layer, textureData);
        }
        layer.prepareQuadList().addAll(quads.getAll());
        if (quads.hasMaterialFlag(BakedQuad.FLAG_ANIMATED))
        {
            output.setAnimated();
        }
    }

    private void remapParticleMaterial(
        final ItemStackRenderState.LayerRenderState layer,
        final MaterialTextureData textureData
    )
    {
        final Material.Baked source = this.properties.particleMaterial();
        final Block target = textureData.getTexturedComponents().get(source.sprite().contents().name());
        if (target == null || target == Blocks.AIR)
        {
            return;
        }

        final Material.Baked replacement = this.targetTextures
            .computeIfAbsent(target, MateriallyTexturedItemModel::loadTargetTextures)
            .particleMaterial();
        if (replacement != null)
        {
            layer.setParticleMaterial(replacement);
        }
    }

    private QuadCollection buildRetexturedQuads(final MaterialTextureData textureData)
    {
        final QuadCollection.Builder builder = new QuadCollection.Builder();
        boolean changed = false;

        for (final BakedQuad quad : this.baseQuads.getQuads(null))
        {
            final BakedQuad replacement = remapQuad(quad, textureData);
            builder.addUnculledFace(replacement);
            changed |= replacement != quad;
        }

        for (final Direction direction : Direction.values())
        {
            for (final BakedQuad quad : this.baseQuads.getQuads(direction))
            {
                final BakedQuad replacement = remapQuad(quad, textureData);
                builder.addCulledFace(direction, replacement);
                changed |= replacement != quad;
            }
        }

        return changed ? builder.build() : this.baseQuads;
    }

    private BakedQuad remapQuad(final BakedQuad source, final MaterialTextureData textureData)
    {
        final Identifier sourceTexture = source.materialInfo().sprite().contents().name();
        final Block target = textureData.getTexturedComponents().get(sourceTexture);
        if (target == null || target == Blocks.AIR)
        {
            return source;
        }

        final TargetTextures textures = this.targetTextures.computeIfAbsent(target, MateriallyTexturedItemModel::loadTargetTextures);
        final TargetSprite targetSprite = textures.forDirection(source.direction());
        if (targetSprite == null)
        {
            return source;
        }

        final BakedQuad.MaterialInfo targetInfo = targetSprite.quad().materialInfo();
        return new MutableQuad()
            .setFrom(source)
            .setSpriteAndMoveUv(targetInfo.sprite(), targetInfo.layer(), targetInfo.itemRenderType())
            .setTintIndex(targetInfo.tintIndex())
            .setShade(targetInfo.shade())
            .setLightEmission(targetInfo.lightEmission())
            .setAmbientOcclusion(targetInfo.ambientOcclusion())
            .toBakedQuad();
    }

    @SuppressWarnings("deprecation")
    private static TargetTextures loadTargetTextures(final Block block)
    {
        try
        {
            final BlockStateModel model = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(block.defaultBlockState());
            final List<BlockStateModelPart> parts = new ArrayList<>();
            model.collectParts(RandomSource.create(0L), parts);
            final EnumMap<Direction, TargetSprite> byDirection = new EnumMap<>(Direction.class);
            TargetSprite unculled = null;

            for (final BlockStateModelPart part : parts)
            {
                final List<BakedQuad> unculledQuads = part.getQuads(null);
                if (unculled == null && !unculledQuads.isEmpty())
                {
                    unculled = targetSprite(unculledQuads.getFirst());
                }

                for (final Direction direction : Direction.values())
                {
                    if (byDirection.containsKey(direction))
                    {
                        continue;
                    }
                    final List<BakedQuad> directionalQuads = part.getQuads(direction);
                    if (!directionalQuads.isEmpty())
                    {
                        byDirection.put(direction, targetSprite(directionalQuads.getFirst()));
                    }
                }
            }

            final Material.Baked particle = model.particleMaterial();
            return new TargetTextures(byDirection, unculled, particle);
        }
        catch (final RuntimeException ignored)
        {
            // A target can be unavailable during an early resource reload. The
            // source quad is safer than dropping the entire item.
            return TargetTextures.EMPTY;
        }
    }

    private static TargetSprite targetSprite(final BakedQuad quad)
    {
        return new TargetSprite(quad);
    }

    private static boolean hasSpecialAnimatedTexture(final ItemStack itemStack)
    {
        return itemStack.is(ItemTags.COMPASSES) || itemStack.is(Items.CLOCK);
    }

    private record TargetSprite(BakedQuad quad)
    {
    }

    private record TargetTextures(
        Map<Direction, TargetSprite> byDirection,
        @Nullable TargetSprite unculled,
        Material.@Nullable Baked particleMaterial
    )
    {
        private static final TargetTextures EMPTY = new TargetTextures(Map.of(), null, null);

        @Nullable
        private TargetSprite forDirection(final Direction direction)
        {
            if (direction != null)
            {
                final TargetSprite directional = this.byDirection.get(direction);
                if (directional != null)
                {
                    return directional;
                }
            }
            return this.unculled;
        }
    }

    public record Unbaked(
        Identifier model,
        Optional<Transformation> transformation,
        List<ItemTintSource> tints
    ) implements ItemModel.Unbaked
    {
        public static final MapCodec<MateriallyTexturedItemModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                    Identifier.CODEC.fieldOf("model").forGetter(MateriallyTexturedItemModel.Unbaked::model),
                    Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").forGetter(MateriallyTexturedItemModel.Unbaked::transformation),
                    ItemTintSources.CODEC.listOf().optionalFieldOf("tints", List.of()).forGetter(MateriallyTexturedItemModel.Unbaked::tints)
                )
                .apply(i, MateriallyTexturedItemModel.Unbaked::new)
        );

        @Override
        public void resolveDependencies(final ResolvableModel.Resolver resolver)
        {
            resolver.markDependency(this.model);
        }

        @Override
        public ItemModel bake(final ItemModel.BakingContext context, final Matrix4fc transformation)
        {
            final ModelBaker baker = context.blockModelBaker();
            final ResolvedModel resolvedModel = baker.getModel(this.model);
            final TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
            final QuadCollection quads = resolvedModel.bakeTopGeometry(textureSlots, baker, BlockModelRotation.IDENTITY);
            final ModelRenderProperties properties = ModelRenderProperties.fromResolvedModel(baker, resolvedModel, textureSlots);
            final Matrix4fc modelTransform = Transformation.compose(transformation, this.transformation);
            return new MateriallyTexturedItemModel(this.tints, quads, properties, modelTransform);
        }

        @Override
        public MapCodec<MateriallyTexturedItemModel.Unbaked> type()
        {
            return MAP_CODEC;
        }
    }
}
