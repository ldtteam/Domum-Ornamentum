package com.ldtteam.domumornamentum.client.model.baked;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.IModelData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MateriallyTexturedBakedModel implements IBakedModel
{

    private static final Logger LOGGER = LogManager.getLogger();

    private final Cache<MaterialTextureData, IBakedModel> cache = CacheBuilder.newBuilder()
        .expireAfterAccess(2, TimeUnit.MINUTES)
        .concurrencyLevel(4)
        .maximumSize(100)
        .build();
    private final IBakedModel innerModel;

    private final ItemOverrideList overrideList = new OverrideList(this);

    public MateriallyTexturedBakedModel(final IBakedModel innerModel) {this.innerModel = innerModel;}

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
        final IBakedModel remappedModel = getBakedInnerModelFor(extraData);
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
    public TextureAtlasSprite getParticleTexture(@NotNull final IModelData modelData)
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
          .getParticleTexture(modelData);
    }

    @Override
    public ItemOverrideList getOverrides()
    {
        return this.overrideList;
    }

    private IBakedModel getBakedInnerModelFor(final IModelData modelData)
    {
        if (!modelData.hasProperty(ModProperties.MATERIAL_TEXTURE_PROPERTY))
        {
            return getBakedInnerModelFor(MaterialTextureData.EMPTY);
        }

        return getBakedInnerModelFor(modelData.getData(ModProperties.MATERIAL_TEXTURE_PROPERTY));
    }

    private IBakedModel getBakedInnerModelFor(final MaterialTextureData modelData)
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

    private static class OverrideList extends ItemOverrideList {

        private final MateriallyTexturedBakedModel model;

        private OverrideList(final MateriallyTexturedBakedModel model) {this.model = model;}

        @Nullable
        @Override
        public IBakedModel resolve(
          final IBakedModel model, final ItemStack stack, @Nullable final ClientWorld world, @Nullable final LivingEntity livingEntity)
        {
            final MaterialTextureData textureData = MaterialTextureData.deserializeFromNBT(stack.getOrCreateTagElement("textureData"));
            return this.model.getBakedInnerModelFor(textureData);
        }
    }
}
