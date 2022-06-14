package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FenceBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;

public abstract class AbstractBlockFence<B extends AbstractBlockFence<B>> extends FenceBlock implements IDOBlock<B>
{
    public AbstractBlockFence(final BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
