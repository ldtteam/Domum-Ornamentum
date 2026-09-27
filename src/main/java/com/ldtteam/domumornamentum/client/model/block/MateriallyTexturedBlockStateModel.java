package com.ldtteam.domumornamentum.client.model.block;

import com.ldtteam.domumornamentum.client.model.RetexturingHandler;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MateriallyTexturedBlockStateModel implements BlockStateModel, DynamicBlockStateModel
{

    private final BlockStateModel parent;

    public MateriallyTexturedBlockStateModel(final BlockStateModel parent) {this.parent = parent;}

    @SuppressWarnings("deprecation")
    @Override
    public Material.@NonNull Baked particleMaterial()
    {
        return parent.particleMaterial();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @BakedQuad.MaterialFlags int materialFlags()
    {
        return parent.materialFlags();
    }

    @Override
    public @Nullable Object createGeometryKey(final BlockAndTintGetter level, final @NonNull BlockPos pos, final @NonNull BlockState state, final @NonNull RandomSource random)
    {
        final BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof MateriallyTexturedBlockEntity materiallyTexturedBlockEntity))
            return null;

        return materiallyTexturedBlockEntity.getTextureData();
    }

    @Override
    public Material.@NonNull Baked particleMaterial(final @NonNull BlockAndTintGetter level, final @NonNull BlockPos pos, final @NonNull BlockState state)
    {
        return parent.particleMaterial(level, pos, state);
    }

    @Override
    public void collectParts(final @NonNull BlockAndTintGetter level, final @NonNull BlockPos pos, final @NonNull BlockState state, final @NonNull RandomSource random, final @NonNull List<BlockStateModelPart> parts)
    {
        final var parentParts = new ArrayList<BlockStateModelPart>();
        parent.collectParts(level, pos, state, random, parentParts);

        final var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MateriallyTexturedBlockEntity texturedBlockEntity) {
            //retexturedParts passes the parent parts through unchanged when the mapping is empty
            parts.addAll(RetexturingHandler.retexturedParts(parentParts, texturedBlockEntity.getTextureData(), pos, level));
        } else {
            parts.addAll(parentParts);
        }
    }
}
