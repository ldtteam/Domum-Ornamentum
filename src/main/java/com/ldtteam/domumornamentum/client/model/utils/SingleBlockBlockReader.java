//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ldtteam.domumornamentum.client.model.utils;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SingleBlockBlockReader implements BlockGetter {
    protected final BlockState blockState;
    protected final BlockPos pos;
    protected final @Nullable BlockGetter source;
    protected final @Nullable BlockEntity blockEntity;

    protected SingleBlockBlockReader(BlockState blockState, BlockPos pos, @Nullable BlockGetter source, @Nullable BlockEntity blockEntity) {
        this.blockState = blockState;
        this.pos = pos;
        this.source = source;
        this.blockEntity = blockEntity;
    }

    public @Nullable BlockEntity getBlockEntity(@NotNull BlockPos pos) {
        if (pos == this.pos) {
            return this.blockEntity;
        } else {
            return this.source == null ? null : this.source.getBlockEntity(pos);
        }
    }

    public @NotNull BlockState getBlockState(@NotNull BlockPos pos) {
        if (pos == this.pos) {
            return this.blockState;
        } else {
            return this.source == null ? Blocks.AIR.defaultBlockState() : this.source.getBlockState(pos);
        }
    }

    public @NotNull FluidState getFluidState(@NotNull BlockPos pos) {
        return this.getBlockState(pos).getFluidState();
    }

    public int getHeight() {
        return 0;
    }

    public int getMinY() {
        return 0;
    }

    public static class Builder {
        private BlockState blockState;
        private BlockPos pos;
        private @Nullable BlockGetter source;
        private Supplier<@Nullable BlockEntity> blockEntityBuilder;

        public Builder() {
            this.pos = BlockPos.ZERO;
        }

        public Builder withBlockState(BlockState blockState) {
            this.blockState = blockState;
            return this;
        }

        public Builder withPos(BlockPos pos) {
            this.pos = pos;
            return this;
        }

        public Builder withSource(@Nullable BlockGetter source) {
            this.source = source;
            return this;
        }

        public Builder withBlockEntity(Supplier<@Nullable BlockEntity> blockEntityBuilder) {
            this.blockEntityBuilder = blockEntityBuilder;
            return this;
        }

        public SingleBlockBlockReader createSingleBlockBlockReader() {
            if (this.blockState == null) {
                throw new IllegalStateException("A blockstate is required for a single block block reader!");
            } else {
                BlockEntity blockEntity = this.blockEntityBuilder != null ? (BlockEntity)this.blockEntityBuilder.get() : null;
                return new SingleBlockBlockReader(this.blockState, this.pos, this.source, blockEntity);
            }
        }
    }
}
