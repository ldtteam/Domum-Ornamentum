package com.ldtteam.domumornamentum.client.model.baked;

import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.client.model.utils.ModelUVAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
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
import net.minecraftforge.client.model.data.ModelData;
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
      final BakedModel target
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

       if (!itemStackMode && bakedModel.getRenderTypes(target.defaultBlockState(), RANDOM, ModelData.EMPTY).contains(this.renderType)) {
           return this.with(source, bakedModel);
       } else if (itemStackMode && bakedModel.getRenderTypes(new ItemStack(target), Minecraft.useShaderTransparency()).contains(getRenderTypeAdapted())) {
           return this.with(source, bakedModel);
       }

       return this;
    }

    public BakedModel build() {
        final SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(
                sourceModel.useAmbientOcclusion(this.sourceState, getRenderTypeAdapted()),
                sourceModel.usesBlockLight(),
                sourceModel.isGui3d(),
                sourceModel.getTransforms(),
                sourceModel.getOverrides()
        );

        this.target.getQuads(null, null, RANDOM, ModelData.EMPTY, getRenderTypeAdapted()).forEach(quad -> {
            if (this.retexturingMaps.containsKey(quad.getSprite().getName()))
            {
                retexture(quad, null).ifPresent(builder::addUnculledFace);
            }
        });

        for (final Direction value : Direction.values())
        {
            this.target.getQuads(null, value, RANDOM, ModelData.EMPTY, getRenderTypeAdapted()).forEach(quad -> {
                if (this.retexturingMaps.containsKey(quad.getSprite().getName()))
                {
                    retexture(quad, value).ifPresent(newQuad -> builder.addCulledFace(value, newQuad));
                }
            });
        }

        TextureAtlasSprite particleTexture = this.target.getParticleIcon(ModelData.EMPTY);
        if (this.retexturingMaps.containsKey(particleTexture.getName()))
        {
            final BakedModel particleOverrideTextureModel = this.retexturingMaps.get(particleTexture.getName());
            particleTexture = particleOverrideTextureModel.getParticleIcon(ModelData.EMPTY);
        }
        builder.particle(particleTexture);

        return builder.build();
    }

    private Optional<BakedQuad> retexture(@NotNull BakedQuad quad, @Nullable Direction direction)
    {
        if (!this.retexturingMaps.containsKey(quad.getSprite().getName()))
            return Optional.empty();

        final Optional<TextureAtlasSprite> retexturingSprite = this.getTexture(quad, direction);

        return retexturingSprite.map(sprite -> {
            final ModelUVAdapter adapter = new ModelUVAdapter(
                    quad,
                    sprite
            );
            return adapter.build();
        });
    }

    private Optional<TextureAtlasSprite> getTexture(@NotNull BakedQuad quad, @Nullable Direction direction)
    {
        if (!retexturingMaps.containsKey(quad.getSprite().getName())) {
            return Optional.empty();
        }

        final BakedModel targetModel = this.retexturingMaps.get(quad.getSprite().getName());
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

    private RenderType getRenderTypeAdapted() {
        if (!this.itemStackMode)
            return this.renderType;

        if (renderType == RenderType.translucent()) {
            return !Minecraft.useShaderTransparency() ? Sheets.translucentCullBlockSheet() : Sheets.translucentItemSheet();
        } else {
            return Sheets.cutoutBlockSheet();
        }
    }
}
