package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FenceBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Map;

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

    public static Map<Direction, BooleanProperty> getDirectionalProperties() {
        return PROPERTY_BY_DIRECTION;
    }
}
