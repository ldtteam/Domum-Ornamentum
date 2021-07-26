package com.ldtteam.domumornamentum.client.model.utils;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;

public abstract class BaseModelReader implements IVertexConsumer
{

    @Override
    public VertexFormat getVertexFormat()
    {
        return DefaultVertexFormat.BLOCK;
    }

    @Override
    public void setQuadTint(
      final int tint )
    {
    }

    @Override
    public void setQuadOrientation(
      final Direction orientation )
    {
    }

    @Override
    public void setApplyDiffuseLighting(
      final boolean diffuse )
    {

    }

    @Override
    public void setTexture(
      final TextureAtlasSprite texture )
    {

    }
}