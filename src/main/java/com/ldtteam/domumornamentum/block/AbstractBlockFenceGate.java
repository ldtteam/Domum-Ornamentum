package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.FenceGateBlock;

public abstract class AbstractBlockFenceGate<B extends AbstractBlockFenceGate<B>> extends FenceGateBlock implements IDOBlock<B>
{
    public AbstractBlockFenceGate(final Properties properties)
    {
        super(properties, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN);
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
