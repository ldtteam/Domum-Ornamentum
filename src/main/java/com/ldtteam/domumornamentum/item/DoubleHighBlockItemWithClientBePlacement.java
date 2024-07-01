package com.ldtteam.domumornamentum.item;

import net.minecraft.world.level.block.Block;

/**
 * BlockItem which places blockEntity data also on client
 */
@Deprecated(forRemoval = true, since = "1.21")
public class DoubleHighBlockItemWithClientBePlacement extends SelfUpgradingDoubleHighBlockItem
{
    public DoubleHighBlockItemWithClientBePlacement(final Block block, final Properties properties)
    {
        super(block, properties);
    }
}
