package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.TrapDoorBlock;

public abstract class AbstractBlockTrapdoor<B extends AbstractBlockTrapdoor<B>> extends TrapDoorBlock implements IDOBlock<B>
{
    public AbstractBlockTrapdoor(final Properties properties)
    {
        super(properties, SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN);
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
