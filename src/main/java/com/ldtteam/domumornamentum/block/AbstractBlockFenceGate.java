package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class AbstractBlockFenceGate<B extends AbstractBlockFenceGate<B>> extends FenceGateBlock implements IDOBlock<B>
{
    public AbstractBlockFenceGate(final Properties properties)
    {
        super(properties, WoodType.OAK);
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
