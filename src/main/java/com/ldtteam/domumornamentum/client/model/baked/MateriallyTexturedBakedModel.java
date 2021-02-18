package com.ldtteam.domumornamentum.client.model.baked;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MateriallyTexturedBakedModel implements IBakedModel
{

    private final IUnbakedModel innerModel;

    public MateriallyTexturedBakedModel(final IUnbakedModel innerModel) {this.innerModel = innerModel;}

    @Override
    public List<BakedQuad> getQuads(
      @Nullable final BlockState state, @Nullable final Direction side, final Random rand)
    {
        return null;
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return false;
    }

    @Override
    public boolean isGui3d()
    {
        return false;
    }

    @Override
    public boolean isSideLit()
    {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return null;
    }

    @Override
    public ItemOverrideList getOverrides()
    {
        return null;
    }
}
