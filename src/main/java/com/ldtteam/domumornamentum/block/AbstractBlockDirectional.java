package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public abstract class AbstractBlockDirectional<B extends AbstractBlockDirectional<B>> extends HorizontalDirectionalBlock implements IDOBlock<B>
{
    public AbstractBlockDirectional(final Properties properties)
    {
        super(properties);
    }

    @Override
    public Identifier getRegistryName()
    {
        return getRegistryName(this);
    }
}
