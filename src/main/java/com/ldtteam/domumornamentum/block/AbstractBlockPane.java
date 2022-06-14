package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.IronBarsBlock;

public abstract class AbstractBlockPane<B extends AbstractBlockPane<B>> extends IronBarsBlock implements IDOBlock<B>
{
    protected AbstractBlockPane(final Properties properties)
    {
        super(properties);
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
