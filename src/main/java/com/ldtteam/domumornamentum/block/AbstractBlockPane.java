package com.ldtteam.domumornamentum.block;

import com.google.common.collect.ImmutableMap;
import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import com.ldtteam.domumornamentum.block.vanilla.WallBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;

public abstract class AbstractBlockPane<B extends AbstractBlockPane<B>> extends IronBarsBlock implements IDOBlock<B>
{
    public static ImmutableMap<Direction, BooleanProperty> PROPERTIES = ImmutableMap.of(
            Direction.NORTH, CrossCollisionBlock.NORTH,
            Direction.EAST, CrossCollisionBlock.EAST,
            Direction.SOUTH, CrossCollisionBlock.SOUTH,
            Direction.WEST, CrossCollisionBlock.WEST
    );

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
