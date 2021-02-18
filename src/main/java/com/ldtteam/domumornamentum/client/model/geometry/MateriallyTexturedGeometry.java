package com.ldtteam.domumornamentum.client.model.geometry;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class MateriallyTexturedGeometry implements IModelGeometry<MateriallyTexturedGeometry>
{

    private final IUnbakedModel innerModel;

    public MateriallyTexturedGeometry(final IUnbakedModel innerModel)
    {
        this.innerModel = innerModel;
    }

    @Override
    public IBakedModel bake(
      final IModelConfiguration owner,
      final ModelBakery bakery,
      final Function<RenderMaterial, TextureAtlasSprite> spriteGetter,
      final IModelTransform modelTransform,
      final ItemOverrideList overrides,
      final ResourceLocation modelLocation)
    {
        return null;
    }

    @Override
    public Collection<RenderMaterial> getTextures(
      final IModelConfiguration owner, final Function<ResourceLocation, IUnbakedModel> modelGetter, final Set<Pair<String, String>> missingTextureErrors)
    {
        return innerModel.getTextures(modelGetter, missingTextureErrors);
    }
}
