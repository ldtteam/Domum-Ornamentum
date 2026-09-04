package com.ldtteam.domumornamentum.client.model.block;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.quad.MutableQuad;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Adds Domum Ornamentum's material component remapping to the modern 26.2
 * block-state renderer.
 *
 * <p>The 1.21 implementation wrapped a Forge {@code BakedModel}.  In 26.2 a
 * block state is rendered from {@link BlockStateModelPart}s instead, so the
 * equivalent has to happen while those parts are collected for a block entity
 * position.  Installing the wrapper after baking keeps the generated
 * multipart/variant files intact and lets every existing DO model participate
 * in the normal model cache.</p>
 */
public final class MateriallyTexturedBlockStateModel implements DynamicBlockStateModel
{
    private static final DirectionKey UNCULLED = new DirectionKey(null);

    private final BlockStateModel delegate;
    private final Map<Block, TargetModel> targetModels;

    private MateriallyTexturedBlockStateModel(
        final BlockStateModel delegate,
        final Map<Block, TargetModel> targetModels
    )
    {
        this.delegate = delegate;
        this.targetModels = targetModels;
    }

    /**
     * Wrap all materialized DO block states after vanilla/NeoForge has baked
     * them.  The target map is captured before replacement so a DO block used
     * as a valid skin never recursively resolves through its own wrapper.
     */
    public static void install(final ModelEvent.ModifyBakingResult event)
    {
        final Map<Block, TargetModel> targets = new HashMap<>();
        for (final Map.Entry<BlockState, BlockStateModel> entry : event.getBakingResult().blockStateModels().entrySet())
        {
            final BlockState state = entry.getKey();
            final Block block = state.getBlock();
            final TargetModel candidate = new TargetModel(state, entry.getValue());

            if (state.equals(block.defaultBlockState()))
            {
                targets.put(block, candidate);
            }
            else
            {
                targets.putIfAbsent(block, candidate);
            }
        }

        final Map<Block, TargetModel> immutableTargets = Map.copyOf(targets);
        event.getBakingResult().blockStateModels().replaceAll((state, model) -> {
            if (!(state.getBlock() instanceof IMateriallyTexturedBlock) || model instanceof MateriallyTexturedBlockStateModel)
            {
                return model;
            }
            return new MateriallyTexturedBlockStateModel(model, immutableTargets);
        });
    }

    @Override
    public void collectParts(
        final BlockAndTintGetter level,
        final BlockPos pos,
        final BlockState state,
        final RandomSource random,
        final List<BlockStateModelPart> parts
    )
    {
        final MaterialTextureData textureData = textureData(level, pos);
        if (textureData.isEmpty())
        {
            delegate.collectParts(level, pos, state, random, parts);
            return;
        }

        final List<BlockStateModelPart> delegateParts = new ArrayList<>();
        delegate.collectParts(level, pos, state, random, delegateParts);
        for (final BlockStateModelPart part : delegateParts)
        {
            parts.add(new MateriallyTexturedBlockStateModelPart(part, textureData, targetModels));
        }
    }

    @Override
    public Object createGeometryKey(
        final BlockAndTintGetter level,
        final BlockPos pos,
        final BlockState state,
        final RandomSource random
    )
    {
        return new GeometryKey(delegate.createGeometryKey(level, pos, state, random), textureData(level, pos));
    }

    @Override
    public Material.Baked particleMaterial(
        final BlockAndTintGetter level,
        final BlockPos pos,
        final BlockState state
    )
    {
        final Material.Baked source = delegate.particleMaterial(level, pos, state);
        final Block target = textureData(level, pos).getTexturedComponents().get(source.sprite().contents().name());
        final TargetModel targetModel = targetModels.get(target);
        if (targetModel == null || target == Blocks.AIR)
        {
            return source;
        }
        return targetModel.model().particleMaterial(BlockAndTintGetter.EMPTY, BlockPos.ZERO, targetModel.state());
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags(
        final BlockAndTintGetter level,
        final BlockPos pos,
        final BlockState state
    )
    {
        final MaterialTextureData textureData = textureData(level, pos);
        int flags = delegate.materialFlags(level, pos, state);
        for (final Block target : textureData.getTexturedComponents().values())
        {
            final TargetModel targetModel = targetModels.get(target);
            if (targetModel != null && target != Blocks.AIR)
            {
                flags |= targetModel.model().materialFlags(
                    BlockAndTintGetter.EMPTY,
                    BlockPos.ZERO,
                    targetModel.state()
                );
            }
        }
        return flags;
    }

    @Override
    @Deprecated
    public void collectParts(final RandomSource random, final List<BlockStateModelPart> parts)
    {
        delegate.collectParts(random, parts);
    }

    @Override
    @Deprecated
    public Material.Baked particleMaterial()
    {
        return delegate.particleMaterial();
    }

    @Override
    @Deprecated
    public @BakedQuad.MaterialFlags int materialFlags()
    {
        return delegate.materialFlags();
    }

    private static MaterialTextureData textureData(final BlockAndTintGetter level, final BlockPos pos)
    {
        final MaterialTextureData value = level.getModelData(pos).get(ModProperties.MATERIAL_TEXTURE_PROPERTY);
        return value == null ? MaterialTextureData.EMPTY : value;
    }

    private record GeometryKey(@Nullable Object delegateKey, MaterialTextureData textureData)
    {
    }

    private record DirectionKey(@Nullable Direction direction)
    {
    }

    private record TargetModel(BlockState state, BlockStateModel model)
    {
    }

    private record TargetSprite(BakedQuad quad)
    {
        private TextureAtlasSprite sprite()
        {
            return quad.materialInfo().sprite();
        }
    }

    private record TargetTextures(
        Map<Direction, TargetSprite> byDirection,
        @Nullable TargetSprite unculled,
        Material.@Nullable Baked particleMaterial,
        @BakedQuad.MaterialFlags int materialFlags
    )
    {
        private static final TargetTextures EMPTY = new TargetTextures(Map.of(), null, null, 0);

        @Nullable
        private TargetSprite forDirection(@Nullable final Direction requested, final Direction normal)
        {
            if (requested != null)
            {
                final TargetSprite directional = byDirection.get(requested);
                if (directional != null)
                {
                    return directional;
                }
            }

            final TargetSprite normalSprite = byDirection.get(normal);
            return normalSprite == null ? unculled : normalSprite;
        }
    }

    private static final class MateriallyTexturedBlockStateModelPart implements BlockStateModelPart
    {
        private final BlockStateModelPart delegate;
        private final MaterialTextureData textureData;
        private final Map<Block, TargetModel> targetModels;
        private final ConcurrentMap<DirectionKey, List<BakedQuad>> retexturedQuads = new ConcurrentHashMap<>();

        private MateriallyTexturedBlockStateModelPart(
            final BlockStateModelPart delegate,
            final MaterialTextureData textureData,
            final Map<Block, TargetModel> targetModels
        )
        {
            this.delegate = delegate;
            this.textureData = textureData;
            this.targetModels = targetModels;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable final Direction direction)
        {
            return retexturedQuads.computeIfAbsent(
                direction == null ? UNCULLED : new DirectionKey(direction),
                this::buildQuads
            );
        }

        @Override
        public boolean useAmbientOcclusion()
        {
            return delegate.useAmbientOcclusion();
        }

        @Override
        public Material.Baked particleMaterial()
        {
            return delegate.particleMaterial();
        }

        @Override
        public @BakedQuad.MaterialFlags int materialFlags()
        {
            int flags = delegate.materialFlags();
            for (final Block target : textureData.getTexturedComponents().values())
            {
                final TargetModel targetModel = targetModels.get(target);
                if (targetModel != null && target != Blocks.AIR)
                {
                    flags |= targetModel.model().materialFlags();
                }
            }
            return flags;
        }

        private List<BakedQuad> buildQuads(final DirectionKey key)
        {
            final List<BakedQuad> sourceQuads = delegate.getQuads(key.direction());
            if (sourceQuads.isEmpty())
            {
                return sourceQuads;
            }

            final List<BakedQuad> result = new ArrayList<>(sourceQuads.size());
            boolean changed = false;
            for (final BakedQuad source : sourceQuads)
            {
                final BakedQuad replacement = remap(source, key.direction());
                if (replacement != null)
                {
                    result.add(replacement);
                }
                changed |= replacement != source;
            }
            return changed ? List.copyOf(result) : sourceQuads;
        }

        @Nullable
        private BakedQuad remap(final BakedQuad source, @Nullable final Direction requestedDirection)
        {
            final Identifier sourceTexture = source.materialInfo().sprite().contents().name();
            final Block target = textureData.getTexturedComponents().get(sourceTexture);
            if (target == null)
            {
                return source;
            }
            if (target == Blocks.AIR)
            {
                return null;
            }

            final TargetTextures textures = targetTextures(target);
            final TargetSprite targetSprite = textures.forDirection(requestedDirection, source.direction());
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

        private TargetTextures targetTextures(final Block target)
        {
            final TargetModel targetModel = targetModels.get(target);
            if (targetModel == null)
            {
                return TargetTextures.EMPTY;
            }

            final List<BlockStateModelPart> parts = new ArrayList<>();
            targetModel.model().collectParts(
                BlockAndTintGetter.EMPTY,
                BlockPos.ZERO,
                targetModel.state(),
                RandomSource.create(0L),
                parts
            );

            final Map<Direction, TargetSprite> byDirection = new HashMap<>();
            TargetSprite unculled = null;
            for (final BlockStateModelPart part : parts)
            {
                final List<BakedQuad> unculledQuads = part.getQuads(null);
                if (unculled == null && !unculledQuads.isEmpty())
                {
                    unculled = new TargetSprite(unculledQuads.getFirst());
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
                        byDirection.put(direction, new TargetSprite(directionalQuads.getFirst()));
                    }
                }
            }

            return new TargetTextures(
                Map.copyOf(byDirection),
                unculled,
                targetModel.model().particleMaterial(BlockAndTintGetter.EMPTY, BlockPos.ZERO, targetModel.state()),
                targetModel.model().materialFlags(BlockAndTintGetter.EMPTY, BlockPos.ZERO, targetModel.state())
            );
        }
    }
}
