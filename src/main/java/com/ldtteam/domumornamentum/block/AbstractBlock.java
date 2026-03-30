package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public abstract class AbstractBlock<B extends AbstractBlock<B>> extends Block implements IDOBlock<B>
{
    public AbstractBlock(final Properties properties)
    {
        super(properties);
    }

    @Override
    public Identifier getRegistryName()
    {
        return getRegistryName(this);
    }
}
