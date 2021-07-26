package com.ldtteam.domumornamentum.client.model.baked;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MateriallyTexturedBakedModel implements BakedModel
{

    private static final Logger LOGGER = LogManager.getLogger();

    private final Cache<MaterialTextureData, BakedModel> cache = CacheBuilder.newBuilder()
        .expireAfterAccess(2, TimeUnit.MINUTES)
        .concurrencyLevel(4)
        .maximumSize(100)
        .build();
    private final BakedModel innerModel;

    private final ItemOverrides overrideList = new OverrideList(this);

    public MateriallyTexturedBakedModel(final BakedModel innerModel) {this.innerModel = innerModel;}

    @Override
    public List<BakedQuad> getQuads(
      @Nullable final BlockState state, @Nullable final Direction side, final Random rand)
    {
        return innerModel.getQuads(state, side, rand);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable final BlockState state, @Nullable final Direction side, @NotNull final Random rand, @NotNull final IModelData extraData)
    {
        final BakedModel remappedModel = getBakedInnerModelFor(extraData);
        return remappedModel.getQuads(state, side, rand, extraData);
    }

    @Override
    public boolean useAmbientOcclusion()
    {
        return innerModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
        return innerModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight()
    {
        return innerModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer()
    {
        return innerModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon()
    {
        return innerModel.getParticleIcon();
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull final IModelData modelData)
    {
        if (!modelData.hasProperty(ModProperties.MATERIAL_TEXTURE_PROPERTY))
        {
            return getParticleIcon();
        }

        final MaterialTextureData textureData = modelData.getData(ModProperties.MATERIAL_TEXTURE_PROPERTY);
        if (textureData == null)
            return getParticleIcon();

        final ResourceLocation particleTextureName = getParticleIcon().getName();
        if (!textureData.getTexturedComponents().containsKey(particleTextureName))
            return getParticleIcon();

        return Minecraft.getInstance().getBlockRenderer().getBlockModel(textureData.getTexturedComponents().get(particleTextureName).defaultBlockState())
          .getParticleIcon(modelData);
    }

    @Override
    public ItemOverrides getOverrides()
    {
        return this.overrideList;
    }

    private BakedModel getBakedInnerModelFor(final IModelData modelData)
    {
        if (!modelData.hasProperty(ModProperties.MATERIAL_TEXTURE_PROPERTY))
        {
            return getBakedInnerModelFor(MaterialTextureData.EMPTY);
        }

        return getBakedInnerModelFor(modelData.getData(ModProperties.MATERIAL_TEXTURE_PROPERTY));
    }

    private BakedModel getBakedInnerModelFor(final MaterialTextureData modelData)
    {
        try
        {
            return cache.get(modelData, () -> {
                final RetexturedBakedModelBuilder builder = RetexturedBakedModelBuilder.createFor(
                  this.innerModel
                );

                modelData.getTexturedComponents().forEach(builder::with);

                return builder.build();
            });
        }
        catch (Exception exception)
        {
            LOGGER.error(String.format("Failed to build baked materially textured model for: %s", modelData), exception);
            return Minecraft.getInstance().getModelManager().getMissingModel();
        }
    }

    private static class OverrideList extends ItemOverrides {

        private final MateriallyTexturedBakedModel model;

        private OverrideList(final MateriallyTexturedBakedModel model) {this.model = model;}

        @Nullable
        @Override
        public BakedModel resolve(
          final BakedModel model,
          final ItemStack stack,
          @Nullable final ClientLevel level,
          @Nullable final LivingEntity entity,
          final int random)
        {
            final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(stack.getOrCreateTagElement("textureData"));
            return this.model.getBakedInnerModelFor(textureData);
        }
    }
}
