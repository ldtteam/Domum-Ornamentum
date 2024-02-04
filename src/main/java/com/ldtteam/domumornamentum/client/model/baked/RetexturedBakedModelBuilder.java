package com.ldtteam.domumornamentum.client.model.baked;

import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.client.model.utils.ModelSpriteQuadTransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.IQuadTransformer;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RetexturedBakedModelBuilder
{

    private static final RandomSource RANDOM = RandomSource.create();

    public static RetexturedBakedModelBuilder createFor(BlockState sourceState, RenderType renderType, boolean itemStackMode, final BakedModel target)
    {
        return new RetexturedBakedModelBuilder(target, sourceState, renderType, itemStackMode, target);
    }

    public static RetexturedBakedModelBuilder createFor(BlockState sourceState, RenderType renderType, boolean itemStackMode, final BakedModel sourceModel, final BakedModel target)
    {
        return new RetexturedBakedModelBuilder(sourceModel, sourceState, renderType, itemStackMode, target);
    }

    private final BakedModel sourceModel;
    private final BakedModel target;
    private final RenderType renderType;
    private final BlockState sourceState;
    private final boolean itemStackMode;
    private final Map<ResourceLocation, BakedModel> retexturingMaps = Maps.newHashMap();

    private RetexturedBakedModelBuilder(final BakedModel sourceModel, BlockState sourceState, RenderType renderType, boolean itemStackMode, final BakedModel target) {
        this.sourceModel = sourceModel;
        this.sourceState = sourceState;
        this.renderType = renderType;
        this.itemStackMode = itemStackMode;
        this.target = target;
    }

    public RetexturedBakedModelBuilder with(
      final ResourceLocation source,
      @Nullable final BakedModel target
    ) {
        this.retexturingMaps.putIfAbsent(source, target);
        return this;
    }

    public RetexturedBakedModelBuilder with(
            final ResourceLocation source,
            final Block target
            ) {
        final BlockState defaultState = target.defaultBlockState();
        final BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(defaultState);

       if (!itemStackMode) {
           if (bakedModel.getRenderTypes(target.defaultBlockState(), RANDOM, ModelData.EMPTY).contains(this.renderType)) {
               return this.with(source, bakedModel);
           }
           else {
               return this.withOut(source);
           }
       } else {
           if (bakedModel.getRenderTypes(new ItemStack(target), Minecraft.useShaderTransparency()).contains(this.renderType)) {
               return this.with(source, bakedModel);
           }
           else {
               return this.withOut(source);
           }
       }

    }

    public RetexturedBakedModelBuilder withOut(
            final ResourceLocation source
    ) {
        this.retexturingMaps.putIfAbsent(source, null);
        return this;
    }

    public BakedModel build() {
        final SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(
                sourceModel.useAmbientOcclusion(this.sourceState, this.renderType),
                sourceModel.usesBlockLight(),
                sourceModel.isGui3d(),
                sourceModel.getTransforms(),
                sourceModel.getOverrides()
        );

        this.target.getQuads(null, null, RANDOM, ModelData.EMPTY, this.renderType).forEach(quad -> {
            if (needsRetexturing(this.retexturingMaps, quad.getSprite()))
            {
                retexture(quad, null).ifPresent(builder::addUnculledFace);
            }
            else if (!needsErasure(this.retexturingMaps, quad.getSprite()))
            {
                builder.addUnculledFace(quad);
            }
        });

        for (final Direction value : Direction.values())
        {
            this.target.getQuads(null, value, RANDOM, ModelData.EMPTY, this.renderType).forEach(quad -> {
                if (needsRetexturing(this.retexturingMaps, quad.getSprite()))
                {
                    retexture(quad, value).ifPresent(newQuad -> builder.addCulledFace(value, newQuad));
                }

                else if (!needsErasure(this.retexturingMaps, quad.getSprite()))
                {
                    builder.addUnculledFace(quad);
                }
            });
        }

        TextureAtlasSprite particleTexture = this.target.getParticleIcon(ModelData.EMPTY);
        if (needsRetexturing(this.retexturingMaps, particleTexture))
        {
            final BakedModel particleOverrideTextureModel = this.retexturingMaps.get(particleTexture.contents().name());
            particleTexture = particleOverrideTextureModel.getParticleIcon(ModelData.EMPTY);
        }
        builder.particle(particleTexture);

        return builder.build();
    }

    private boolean needsRetexturing(Map<ResourceLocation, BakedModel> retexturingMaps, TextureAtlasSprite quad) {
        return retexturingMaps.containsKey(quad.contents().name()) && retexturingMaps.get(quad.contents().name()) != null;
    }

    private boolean needsErasure(Map<ResourceLocation, BakedModel> retexturingMaps, TextureAtlasSprite quad) {
        return retexturingMaps.containsKey(quad.contents().name()) && retexturingMaps.get(quad.contents().name()) == null;
    }

    private Optional<BakedQuad> retexture(@NotNull BakedQuad quad, @Nullable Direction direction)
    {
        if (!needsRetexturing(this.retexturingMaps, quad.getSprite()))
            return Optional.empty();

        if (needsErasure(this.retexturingMaps, quad.getSprite()))
            return Optional.empty();

        final Optional<TextureAtlasSprite> retexturingSprite = this.getTexture(quad, direction);

        return retexturingSprite.map(sprite -> {
            final IQuadTransformer quadTransformer = ModelSpriteQuadTransformer.create(sprite);
            return quadTransformer.process(quad);
        });
    }

    private Optional<TextureAtlasSprite> getTexture(@NotNull BakedQuad quad, @Nullable Direction direction)
    {
        if (!needsRetexturing(retexturingMaps, quad.getSprite())) {
            return Optional.empty();
        }

        if (needsErasure(retexturingMaps, quad.getSprite())) {
            return Optional.empty();
        }

        final BakedModel targetModel = this.retexturingMaps.get(quad.getSprite().contents().name());
        List<BakedQuad> targetQuads = targetModel.getQuads(
          null,
          direction,
          RANDOM,
          ModelData.EMPTY,
          this.renderType
        );

        //If we did not find anything, that might be because the target model specifies culling while our source did not.
        //Lets try with the targeting direction (the normal) of the quad itself.
        if (targetQuads.isEmpty())
            targetQuads = targetModel.getQuads(
              null,
              quad.getDirection(),
              RANDOM,
              ModelData.EMPTY,
              this.renderType
            );

        if(targetQuads.isEmpty())
            return Optional.empty();

        return Optional.of(targetQuads.get(0).getSprite());
    }
}
