package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class AbstractBlockFenceGate<B extends AbstractBlockFenceGate<B>> extends FenceGateBlock implements IDOBlock<B>
{
    public AbstractBlockFenceGate(final Properties properties)
    {
        super(WoodType.OAK, properties);
    }

    @Override
    public Identifier getRegistryName()
    {
        return getRegistryName(this);
    }
}
