package com.ldtteam.domumornamentum.client.model.utils;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ModelUVAdapter extends BaseModelReader
{
    private final BakedQuad source;
    private final float minU;
    private final float uDelta;

    private final float minV;
    private final float vDelta;

    private final TextureAtlasSprite target;
    private final BakedQuadBuilder bakedQuadBuilder;

    @Override
    public void setQuadTint(
      final int tint )
    {
        this.bakedQuadBuilder.setQuadTint(tint);
    }

    @Override
    public void setQuadOrientation(
      final Direction orientation )
    {
        this.bakedQuadBuilder.setQuadOrientation(orientation);
    }

    @Override
    public void setApplyDiffuseLighting(
      final boolean diffuse )
    {
        this.bakedQuadBuilder.setApplyDiffuseLighting(diffuse);
    }

    @Override
    public void setTexture(
      final TextureAtlasSprite texture )
    {
        this.bakedQuadBuilder.setTexture(texture == this.source.getSprite() ? this.target : texture);
    }

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
        this.bakedQuadBuilder = new BakedQuadBuilder();

        this.bakedQuadBuilder.setTexture(target);
        this.bakedQuadBuilder.setQuadTint(source.getTintIndex());
        this.bakedQuadBuilder.setQuadOrientation(source.getDirection());
        this.bakedQuadBuilder.setApplyDiffuseLighting(source.isShade());
    }


    @Override
    public void put(
      final int element,
      @NotNull float... data )
    {
        final VertexFormat format = getVertexFormat();
        final VertexFormatElement ele = format.getElements().get(element);


        if ( ele.getUsage() == VertexFormatElement.Usage.UV && ele.getIndex() == 0)
        {
            final float[] uv = Arrays.copyOf( data, data.length );

            final float u = ( uv[0] - minU ) / uDelta;
            final float v = ( uv[1] - minV ) / vDelta;

            final float newU = this.target.getU(u * 16);
            final float newV = this.target.getV(v * 16);

            final float[] newUv = new float[4];
            newUv[0] = newU;
            newUv[1] = newV;

            data = Arrays.copyOf( newUv, newUv.length );
        }

        this.bakedQuadBuilder.put(element, data);
    }

    public BakedQuad build() {
        this.source.pipe(this);
        return this.bakedQuadBuilder.build();
    }
}
