package com.ldtteam.domumornamentum.client.model.utils;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.core.Direction;

public abstract class BaseModelReader implements VertexConsumer
{


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