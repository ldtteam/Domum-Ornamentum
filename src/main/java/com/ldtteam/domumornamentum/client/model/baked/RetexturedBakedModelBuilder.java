package com.ldtteam.domumornamentum.client.model.baked;

import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.client.model.utils.ModelUVAdapter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RetexturedBakedModelBuilder
{

    private static final Random RANDOM = new Random();

    public static RetexturedBakedModelBuilder createFor(final IBakedModel target)
    {
        return new RetexturedBakedModelBuilder(target);
    }

    private final IBakedModel target;
    private final Map<ResourceLocation, IBakedModel> retexturingMaps = Maps.newHashMap();

    private RetexturedBakedModelBuilder(final IBakedModel target) {this.target = target;}

    public RetexturedBakedModelBuilder with(
      final ResourceLocation source,
      final IBakedModel target
    ) {
        this.retexturingMaps.putIfAbsent(source, target);
        return this;
    }

    public RetexturedBakedModelBuilder with(
      final ResourceLocation source,
      final Block target
    ) {
        final BlockState defaultState = target.defaultBlockState();
        final IBakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(defaultState);

        return this.with(source, bakedModel);
    }

    public IBakedModel build() {
        final SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(
          new IModelConfiguration() {
              @Nullable
              @Override
              public IUnbakedModel getOwnerModel()
              {
                  return null;
              }

              @Override
              public String getModelName()
              {
                  return "Retextured DO Model.";
              }

              @Override
              public boolean isTexturePresent(final String name)
              {
                  return false;
              }

              @Override
              public RenderMaterial resolveTexture(final String name)
              {
                  return null;
              }

              @Override
              public boolean isShadedInGui()
              {
                  return target.isGui3d();
              }

              @Override
              public boolean isSideLit()
              {
                  return target.usesBlockLight();
              }

              @Override
              public boolean useSmoothLighting()
              {
                  return target.useAmbientOcclusion();
              }

              @Override
              public ItemCameraTransforms getCameraTransforms()
              {
                  return target.getTransforms();
              }

              @Override
              public IModelTransform getCombinedTransform()
              {
                  return null;
              }
          },
          target.getOverrides()
        );

        this.target.getQuads(null, null, RANDOM, EmptyModelData.INSTANCE).forEach(quad -> {
            if (!this.retexturingMaps.containsKey(quad.getSprite().getName()))
            {
                builder.addUnculledFace(quad);
            }
            else
            {
                builder.addUnculledFace(retexture(quad, null));
            }
        });

        for (final Direction value : Direction.values())
        {
            this.target.getQuads(null, value, RANDOM, EmptyModelData.INSTANCE).forEach(quad -> {
                if (this.retexturingMaps.containsKey(quad.getSprite().getName()))
                {
                    builder.addCulledFace(value, retexture(quad, value));
                }
                else
                {
                    builder.addCulledFace(value, quad);
                }
            });
        }

        TextureAtlasSprite particleTexture = this.target.getParticleTexture(EmptyModelData.INSTANCE);
        if (this.retexturingMaps.containsKey(particleTexture.getName()))
        {
            final IBakedModel particleOverrideTextureModel = this.retexturingMaps.get(particleTexture.getName());
            particleTexture = particleOverrideTextureModel.getParticleTexture(EmptyModelData.INSTANCE);
        }
        builder.particle(particleTexture);

        return builder.build();
    }

    private BakedQuad retexture(@NotNull BakedQuad quad, @Nullable Direction direction)
    {
        if (!this.retexturingMaps.containsKey(quad.getSprite().getName()))
            return quad;

        final TextureAtlasSprite retexturingSprite = this.getTexture(quad, direction);

        final ModelUVAdapter adapter = new ModelUVAdapter(
          quad,
          retexturingSprite
        );
        return adapter.build();
    }

    private TextureAtlasSprite getTexture(@NotNull BakedQuad quad, @Nullable Direction direction)
    {
        final IBakedModel targetModel = this.retexturingMaps.get(quad.getSprite().getName());
        List<BakedQuad> targetQuads = targetModel.getQuads(
          null,
          direction,
          RANDOM,
          EmptyModelData.INSTANCE
        );

        //If we did not find anything, that might be because the target model specifies culling while our source did not.
        //Lets try with the targeting direction (the normal) of the quad itself.
        if (targetQuads.isEmpty())
            targetQuads = targetModel.getQuads(
              null,
              quad.getDirection(),
              RANDOM,
              EmptyModelData.INSTANCE
            );

        if(targetQuads.isEmpty())
            return quad.getSprite();

        return targetQuads.get(0).getSprite();
    }
}
