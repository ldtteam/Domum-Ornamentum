package com.ldtteam.domumornamentum.client.color;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MateriallyTexturedBlockBlockColor implements BlockTintSource {
    public static final MateriallyTexturedBlockBlockColor INSTANCE = new MateriallyTexturedBlockBlockColor();

    @Override
    public int color(final BlockState state) {
        return 0xffffffff;
    }

    @Override
    public int colorInWorld(final BlockState state, @Nullable final BlockAndTintGetter level, @Nullable final BlockPos pos) {
        return 0xffffffff;
    }
}
