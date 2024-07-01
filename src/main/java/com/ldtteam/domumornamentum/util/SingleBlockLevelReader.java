package com.ldtteam.domumornamentum.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class SingleBlockLevelReader extends SingleBlockBlockReader implements LevelReader
{
    @Nullable
    private final LevelReader reader;

    public SingleBlockLevelReader(BlockState state) {
        this(state, null);
    }

    public SingleBlockLevelReader(final BlockState state, final Block blk, final @Nullable LevelReader reader)
    {
        super(state, blk, reader);
        this.reader = reader;
    }

    public SingleBlockLevelReader(final BlockState state, final @Nullable LevelReader reader)
    {
        super(state, state.getBlock(), reader);
        this.reader = reader;
    }

    public SingleBlockLevelReader(final BlockState state, final Block blk, final BlockPos pos, final @Nullable LevelReader reader)
    {
        super(state, blk, pos, reader);
        this.reader = reader;
    }

    public SingleBlockLevelReader(final BlockState state, final BlockPos pos, final @Nullable LevelReader reader)
    {
        super(state, pos, reader);
        this.reader = reader;
    }

    @Nullable
    @Override
    public ChunkAccess getChunk(final int x, final int z, @NotNull final ChunkStatus requiredStatus, final boolean nonnull)
    {
        if (this.reader == null)
            return null;

        return this.reader.getChunk(x, z, requiredStatus, nonnull);
    }

    @Override
    public boolean hasChunk(final int chunkX, final int chunkZ)
    {
        return this.reader != null && this.reader.hasChunk(chunkX, chunkZ);
    }

    @Override
    public int getHeight(@NotNull final Heightmap.Types heightmapType, final int x, final int z)
    {
        if (this.reader == null)
            return 0;

        return this.reader.getHeight(heightmapType, x, z);
    }

    @Override
    public int getSkyDarken()
    {
        return 15;
    }

    @NotNull
    @Override
    public BiomeManager getBiomeManager()
    {
        if (this.reader == null)
            throw new IllegalStateException("No reader available.");

        return this.reader.getBiomeManager();
    }

    @NotNull
    @Override
    public Holder<Biome> getUncachedNoiseBiome(final int x, final int y, final int z)
    {
        if (this.reader == null)
            throw new IllegalStateException("No reader available.");

        return this.reader.getUncachedNoiseBiome(x, y, z);
    }

    @Override
    public boolean isClientSide()
    {
        return this.reader == null || this.reader.isClientSide();
    }

    @Override
    public int getSeaLevel()
    {
        if (this.reader == null)
            return 63;

        return this.reader.getSeaLevel();
    }

    @NotNull
    @Override
    public DimensionType dimensionType()
    {
        if (this.reader == null)
            throw new IllegalStateException("No reader available.");

        return this.reader.dimensionType();
    }

    @Override
    public @NotNull RegistryAccess registryAccess() {
        if (this.reader == null)
            return RegistryAccess.EMPTY;

        return this.reader.registryAccess();
    }

    @Override
    public @NotNull FeatureFlagSet enabledFeatures() {
        if (this.reader == null)
            return FeatureFlagSet.of();

        return this.reader.enabledFeatures();
    }

    @Override
    public float getShade(@NotNull final Direction p_230487_1_, final boolean p_230487_2_)
    {
        if (this.reader == null)
            return 0;

        return this.reader.getShade(p_230487_1_, p_230487_2_);
    }

    @NotNull
    @Override
    public LevelLightEngine getLightEngine()
    {
        if (this.reader == null)
            throw new IllegalStateException("No reader available.");

        return this.reader.getLightEngine();
    }

    @NotNull
    @Override
    public WorldBorder getWorldBorder()
    {
        if (this.reader == null)
            throw new IllegalStateException("No reader available.");

        return this.reader.getWorldBorder();
    }

    @NotNull
    @Override
    public List<VoxelShape> getEntityCollisions(@Nullable final Entity entity, final @NotNull AABB aabb)
    {
        if (this.reader == null || entity == null)
            return List.of();

        return this.reader.getEntityCollisions(entity, aabb);
    }

    @Nullable
    @Override
    public BlockEntity getBlockEntity(@NotNull final BlockPos pos)
    {
        if (pos == this.pos && blk instanceof EntityBlock)
        {
            return ((EntityBlock) blk).newBlockEntity(this.pos, state);
        }

        if (this.reader == null)
            return null;

        return this.reader.getBlockEntity(pos);
    }

    @NotNull
    @Override
    public BlockState getBlockState(@NotNull final BlockPos pos)
    {
        if (pos == this.pos)
        {
            return state;
        }

        if (this.reader == null)
            return Blocks.AIR.defaultBlockState();

        return this.reader.getBlockState(pos);
    }

    @NotNull
    @Override
    public FluidState getFluidState(@NotNull final BlockPos pos)
    {
        return this.getBlockState(pos).getFluidState();
    }
}
