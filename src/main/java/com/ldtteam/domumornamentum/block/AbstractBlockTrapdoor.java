package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public abstract class AbstractBlockTrapdoor<B extends AbstractBlockTrapdoor<B>> extends TrapDoorBlock implements IDOBlock<B>
{
    public AbstractBlockTrapdoor(final Properties properties)
    {
        super(BlockSetType.OAK, properties);
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
