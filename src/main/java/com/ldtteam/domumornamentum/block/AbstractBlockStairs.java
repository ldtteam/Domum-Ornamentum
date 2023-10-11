package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public abstract class AbstractBlockStairs<B extends AbstractBlockStairs<B>> extends DOStairBlock implements IDOBlock<B>
{
    public AbstractBlockStairs(final Supplier<BlockState> state, final Properties properties)
    {
        super(state, properties);
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
