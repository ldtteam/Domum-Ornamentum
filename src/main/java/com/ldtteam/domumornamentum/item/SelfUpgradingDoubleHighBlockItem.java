package com.ldtteam.domumornamentum.item;

import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

/**
 * BlockItem with own DFU on tag load/set
 */
public class SelfUpgradingDoubleHighBlockItem extends DoubleHighBlockItem
{
    public SelfUpgradingDoubleHighBlockItem(final Block block, final Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void verifyComponentsAfterLoad(final ItemStack itemStack)
    {
        super.verifyComponentsAfterLoad(itemStack);
        SelfUpgradingBlockItem.upgrade(itemStack);
    }
}