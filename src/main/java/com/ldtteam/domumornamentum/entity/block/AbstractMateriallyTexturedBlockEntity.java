package com.ldtteam.domumornamentum.entity.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Abstract MateriallyTexturedBlockEntity.
 */
public abstract class AbstractMateriallyTexturedBlockEntity extends BlockEntity implements IMateriallyTexturedBlockEntity
{
    /**
     * Abstract constructor.
     * @param type blockentity type.
     * @param pos creation pos.
     * @param state creation state.
     */
    public AbstractMateriallyTexturedBlockEntity(
        final BlockEntityType<?> type,
        final BlockPos pos,
        final BlockState state)
    {
        super(type, pos, state);
    }
}
