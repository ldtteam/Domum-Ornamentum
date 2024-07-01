package com.ldtteam.domumornamentum.client.model.geometry;

import com.ldtteam.domumornamentum.client.model.baked.MateriallyTexturedBakedModel;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

public class MateriallyTexturedGeometry implements IUnbakedGeometry<MateriallyTexturedGeometry>
{
    private final ResourceLocation innerModelLocation;

    public MateriallyTexturedGeometry(final ResourceLocation innerModelLocation)
    {
        this.innerModelLocation = innerModelLocation;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
        final UnbakedModel innerModel = baker.getModel(this.innerModelLocation);

        if (!(innerModel instanceof BlockModel)) {
            throw new IllegalStateException("BlockModel parent has to be a block model.");
        }

        final BakedModel innerBakedModel = innerModel.bake(
                baker, spriteGetter, modelState
        );

        return new MateriallyTexturedBakedModel(innerBakedModel);
    }
}
