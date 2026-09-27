//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ldtteam.domumornamentum.client.model.utils;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class SingleBlockBlockAndTintGetter extends SingleBlockBlockReader implements BlockAndTintGetter {
    private final @Nullable BlockAndTintGetter source;

    protected SingleBlockBlockAndTintGetter(BlockState blockState, BlockPos pos, @Nullable BlockAndTintGetter source, @Nullable BlockEntity blockEntity) {
        super(blockState, pos, source, blockEntity);
        this.source = source;
    }

    public @NotNull LevelLightEngine getLightEngine() {
        if (this.source == null) {
            throw new IllegalStateException("No reader available.");
        } else {
            return this.source.getLightEngine();
        }
    }

    public int getBlockTint(@NotNull BlockPos blockPos, @NotNull ColorResolver colorResolver) {
        return this.source == null ? -1 : this.source.getBlockTint(blockPos, colorResolver);
    }

    public @NonNull CardinalLighting cardinalLighting() {
        return this.source == null ? CardinalLighting.DEFAULT : this.source.cardinalLighting();
    }

    public static class Builder {
        private BlockState blockState;
        private BlockPos pos;
        private @Nullable BlockAndTintGetter source;
        private Supplier<@Nullable BlockEntity> blockEntityBuilder;

        public Builder() {
        }

        public Builder withBlockState(BlockState blockState) {
            this.blockState = blockState;
            return this;
        }

        public Builder withPos(BlockPos pos) {
            this.pos = pos;
            return this;
        }

        public Builder withSource(@Nullable BlockAndTintGetter source) {
            this.source = source;
            return this;
        }

        public Builder withSource(@Nullable ClientLevel source) {
            this.source = source;
            return this;
        }

        public Builder withSource(final @Nullable LevelAccessor level) {
            if (level == null) {
                this.source = null;
                return this;
            } else {
                this.source = new BlockAndTintGetter() {
                    {
                        Objects.requireNonNull(Builder.this);
                    }

                    public @NonNull CardinalLighting cardinalLighting() {
                        return level.dimensionType().cardinalLightType().get();
                    }

                    public int getBlockTint(@NonNull BlockPos pos, @NonNull ColorResolver colorResolver) {
                        int dist = (Integer)Minecraft.getInstance().options.biomeBlendRadius().get();
                        if (dist == 0) {
                            return colorResolver.getColor((Biome)level.getBiome(pos).value(), (double)pos.getX(), (double)pos.getZ());
                        } else {
                            int count = (dist * 2 + 1) * (dist * 2 + 1);
                            int totalRed = 0;
                            int totalGreen = 0;
                            int totalBlue = 0;
                            Cursor3D cursor = new Cursor3D(pos.getX() - dist, pos.getY(), pos.getZ() - dist, pos.getX() + dist, pos.getY(), pos.getZ() + dist);

                            int color;
                            for(BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos(); cursor.advance(); totalBlue += ARGB.blue(color)) {
                                nextPos.set(cursor.nextX(), cursor.nextY(), cursor.nextZ());
                                color = colorResolver.getColor((Biome)level.getBiome(nextPos).value(), (double)nextPos.getX(), (double)nextPos.getZ());
                                totalRed += ARGB.red(color);
                                totalGreen += ARGB.green(color);
                            }

                            return ARGB.color(totalRed / count, totalGreen / count, totalBlue / count);
                        }
                    }

                    public @NonNull LevelLightEngine getLightEngine() {
                        return level.getLightEngine();
                    }

                    public @org.jspecify.annotations.Nullable BlockEntity getBlockEntity(@NonNull BlockPos pos) {
                        return level.getBlockEntity(pos);
                    }

                    public @NonNull BlockState getBlockState(@NonNull BlockPos pos) {
                        return level.getBlockState(pos);
                    }

                    public @NonNull FluidState getFluidState(@NonNull BlockPos pos) {
                        return level.getFluidState(pos);
                    }

                    public int getHeight() {
                        return level.getHeight();
                    }

                    public int getMinY() {
                        return level.getMinY();
                    }
                };
                return this;
            }
        }

        public Builder withBlockEntity(Supplier<@Nullable BlockEntity> blockEntityBuilder) {
            this.blockEntityBuilder = blockEntityBuilder;
            return this;
        }

        public SingleBlockBlockAndTintGetter createSingleBlockBlockAndTintGetter() {
            if (this.blockState == null) {
                throw new IllegalStateException("A blockstate is required for a single block block and tint getter!");
            } else {
                BlockEntity blockEntity = this.blockEntityBuilder != null ? (BlockEntity)this.blockEntityBuilder.get() : null;
                return new SingleBlockBlockAndTintGetter(this.blockState, this.pos, this.source, blockEntity);
            }
        }
    }
}
