package com.ldtteam.domumornamentum.client.model.utils;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.neoforged.neoforge.client.model.IQuadTransformer;

public class ModelSpriteQuadTransformer implements IQuadTransformer
{
    public static IQuadTransformer create(final TextureAtlasSprite target) {
        return new ModelSpriteQuadTransformer(target);
    }

    private final TextureAtlasSprite target;

    private ModelSpriteQuadTransformer(
      final TextureAtlasSprite target
    )
    {
        this.target = target;
    }

    @Override
    public void processInPlace(final BakedQuad quad)
    {

        final float minU = quad.getSprite().getU0();
        final float uDelta = quad.getSprite().getU1() - minU;

        final float minV = quad.getSprite().getV0();
        final float vDelta = quad.getSprite().getV1() - minV;
        
        quad.sprite = target;

        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++)
        {
            final float[] uv = new float[2];
            int offset = vertexIndex * STRIDE + UV0;
            uv[0] = Float.intBitsToFloat(quad.getVertices()[offset]);
            uv[1] = Float.intBitsToFloat(quad.getVertices()[offset + 1]);

            final float u = ( uv[0] - minU ) / uDelta;
            final float v = ( uv[1] - minV ) / vDelta;

            final float newU = this.target.getU(u);
            final float newV = this.target.getV(v);

            quad.getVertices()[offset] = Float.floatToRawIntBits(newU);
            quad.getVertices()[offset + 1] = Float.floatToRawIntBits(newV);
        }
    }
}
