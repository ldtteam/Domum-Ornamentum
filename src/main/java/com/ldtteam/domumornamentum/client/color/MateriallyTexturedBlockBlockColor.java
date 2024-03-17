package com.ldtteam.domumornamentum.client.color;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MateriallyTexturedBlockBlockColor implements BlockColor {

    private static final int TINT_MASK = 0xff;
    private static final int TINT_BITS = 8;

    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
        final int blockStateId = tintIndex >> TINT_BITS;
        final BlockState containedState = Block.stateById(blockStateId);
        int tintValue = tintIndex & TINT_MASK;
        if (state.getBlock() instanceof IMateriallyTexturedBlock block && !block.usesWorldSpecificTinting()) {
            return Minecraft.getInstance().getBlockColors().getColor(containedState, null, null, tintValue);
        }

        return Minecraft.getInstance().getBlockColors().getColor(containedState, level, pos, tintValue);
    }
}
