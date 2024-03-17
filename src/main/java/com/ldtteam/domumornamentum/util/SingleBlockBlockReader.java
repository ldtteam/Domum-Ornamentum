package com.ldtteam.domumornamentum.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SingleBlockBlockReader implements BlockGetter
{

    protected final BlockState state;
    protected final Block            blk;
    protected final BlockPos pos;
    protected final BlockGetter source;

    public SingleBlockBlockReader(final BlockState state, final Block blk)
    {
        this.state = state;
        this.blk = blk;
        this.pos = BlockPos.ZERO;
        this.source = null;
    }

    public SingleBlockBlockReader(final BlockState state)
    {
        this.state = state;
        this.blk = state.getBlock();
        this.pos = BlockPos.ZERO;
        this.source = null;
    }

    public SingleBlockBlockReader(final BlockState state, final Block blk, final BlockPos pos)
    {
        this.state = state;
        this.blk = blk;
        this.pos = pos;
        this.source = null;
    }

    public SingleBlockBlockReader(final BlockState state, final BlockPos pos)
    {
        this.state = state;
        this.blk = state.getBlock();
        this.pos = pos;
        this.source = null;
    }

    public SingleBlockBlockReader(final BlockState state, final Block blk, final BlockGetter source)
    {
        this.state = state;
        this.blk = blk;
        this.source = source;
        this.pos = BlockPos.ZERO;
    }

    public SingleBlockBlockReader(final BlockState state, final BlockGetter source)
    {
        this.state = state;
        this.blk = state.getBlock();
        this.source = source;
        this.pos = BlockPos.ZERO;
    }

    public SingleBlockBlockReader(final BlockState state, final Block blk, final BlockPos pos, final BlockGetter source)
    {
        this.state = state;
        this.blk = blk;
        this.pos = pos;
        this.source = source;
    }

    public SingleBlockBlockReader(final BlockState state, final BlockPos pos, final BlockGetter source)
    {
        this.state = state;
        this.blk = state.getBlock();
        this.pos = pos;
        this.source = source;
    }

    @Nullable
    @Override
    public BlockEntity getBlockEntity(@NotNull final BlockPos pos)
    {
        if (pos == this.pos && blk instanceof EntityBlock)
        {
            return ((EntityBlock) blk).newBlockEntity(this.pos, state);
        }

        return source == null ? null : source.getBlockEntity(pos);
    }

    @NotNull
    @Override
    public BlockState getBlockState(@NotNull final BlockPos pos)
    {
        if (pos == this.pos)
        {
            return state;
        }

        return source == null ? Blocks.AIR.defaultBlockState() : source.getBlockState(pos);
    }

    @NotNull
    @Override
    public FluidState getFluidState(@NotNull final BlockPos pos)
    {
        return getBlockState(pos).getFluidState();
    }

    @Override
    public int getHeight()
    {
        return 0;
    }

    @Override
    public int getMinBuildHeight()
    {
        return 0;
    }
}
