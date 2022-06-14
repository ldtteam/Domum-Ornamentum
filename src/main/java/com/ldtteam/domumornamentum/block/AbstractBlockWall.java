package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * The abstract class for structurize-added walls.
 * Currently only used by brown brick, beige brick, and cream brick walls.
 */
public abstract class AbstractBlockWall<B extends AbstractBlockWall<B>> extends WallBlock implements IDOBlock<B>
{
    /**
     * Create a new instance of a wall block.
     *
     * @param properties the properties of the wall block.
     */
    public AbstractBlockWall(final BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }
}
