package com.ldtteam.domumornamentum.client.model.utils;

import com.ldtteam.domumornamentum.util.SingleBlockLevelReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.model.IQuadTransformer;
import net.neoforged.neoforge.client.model.QuadTransformers;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class ModelSpriteQuadTransformer implements IQuadTransformer
{
    public static IQuadTransformer create(final ModelSpriteQuadTransformerData target) {
        return new ModelSpriteQuadTransformer(target);
    }

    private final ModelSpriteQuadTransformerData target;

    private ModelSpriteQuadTransformer(
      final ModelSpriteQuadTransformerData target
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
        
        quad.sprite = target.quad().sprite;

        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++)
        {
            final float[] uv = new float[2];
            int offset = vertexIndex * STRIDE + UV0;
            uv[0] = Float.intBitsToFloat(quad.getVertices()[offset]);
            uv[1] = Float.intBitsToFloat(quad.getVertices()[offset + 1]);

            final float u = ( uv[0] - minU ) / uDelta;
            final float v = ( uv[1] - minV ) / vDelta;

            final float newU = this.target.quad().sprite.getU(u);
            final float newV = this.target.quad().sprite.getV(v);

            quad.getVertices()[offset] = Float.floatToRawIntBits(newU);
            quad.getVertices()[offset + 1] = Float.floatToRawIntBits(newV);
        }

        int color = getColorFor(target.state());
        int tint = target.quad().tintIndex;

        color = tint != -1 ? color : 0xffffffff;

        if (0x00 <= tint && tint <= 0xff) {
            color = 0xffffffff;
            tint = (Block.getId(target.state()) << 8) | tint;
        } else {
            tint = -1;
        }

        if (color != -1) {
            QuadTransformers.applyingColor(color).processInPlace(quad);
        }

        if (tint != -1) {
            quad.tintIndex = tint;
        }
    }

    private int getColorFor(final BlockState state) {
        int out;
        final Fluid fluid = state.getFluidState().getType();
        if (fluid != Fluids.EMPTY) {
            out = IClientFluidTypeExtensions.of(fluid).getTintColor(new FluidStack(fluid, 1));
        } else {
            final ItemStack target = getItemStackFromBlockState(state);

            if (target.isEmpty()) {
                out = 0xffffff;
            } else {
                out = Minecraft.getInstance().getItemColors().getColor(target, 0);
            }
        }

        return out;
    }
    
    private static ItemStack getItemStackFromBlockState(BlockState state) {
        if (state.getBlock() instanceof LiquidBlock)
        {
            return new ItemStack(state.getFluidState().getType().getBucket());
        }

        final Item item = getItem(state);
        if (item != Items.AIR && item != null)
        {
            return new ItemStack(item, 1);
        }

        return new ItemStack(state.getBlock(), 1);
    }

    private static Item getItem(@NotNull final BlockState state)
    {
        final Block block = state.getBlock();
        if (block.equals(Blocks.LAVA))
        {
            return Items.LAVA_BUCKET;
        }
        else if (block instanceof CropBlock)
        {
            final ItemStack stack = block.getCloneItemStack(new SingleBlockLevelReader(state), BlockPos.ZERO, state);
            if (!stack.isEmpty())
            {
                return stack.getItem();
            }

            return Items.WHEAT_SEEDS;
        }
        // oh no...
        else if (block instanceof FarmBlock || block instanceof DirtPathBlock)
        {
            return Blocks.DIRT.asItem();
        }
        else if (block instanceof FireBlock)
        {
            return Items.FLINT_AND_STEEL;
        }
        else if (block instanceof FlowerPotBlock)
        {
            return Items.FLOWER_POT;
        }
        else if (block == Blocks.BAMBOO_SAPLING)
        {
            return Items.BAMBOO;
        }
        else
        {
            return block.asItem();
        }
    }
}
