package com.ldtteam.domumornamentum.client.model.utils;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ModelUVAdapter extends QuadBakingVertexConsumer
{
    private final BakedQuad source;
    private final float minU;
    private final float uDelta;

    private final float minV;
    private final float vDelta;

    private final TextureAtlasSprite target;
    private BakedQuad result;

    public ModelUVAdapter(
      final BakedQuad source,
      final TextureAtlasSprite target
    )
    {

        this.source = source;
        this.minU = source.getSprite().getU0();
        this.uDelta = source.getSprite().getU1() - minU;

        this.minV = source.getSprite().getV0();
        this.vDelta = source.getSprite().getV1() - minV;

        this.target = target;
    }

    public BakedQuad getResult() {
        return result;
    }

    public void setResult(BakedQuad result) {
        this.result = result;
    }

    public BakedQuad build() {
        this.source.pipe(this);
        return this.bakedQuadBuilder.build();
    }


}
