package com.ldtteam.domumornamentum.item;

import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;

import static com.ldtteam.domumornamentum.util.Constants.BLOCK_ENTITY_TEXTURE_DATA;
import static com.ldtteam.domumornamentum.util.Constants.TYPE_BLOCK_PROPERTY;

/**
 * BlockItem with own DFU on tag load/set
 */
public class SelfUpgradingBlockItem extends BlockItem
{
    public SelfUpgradingBlockItem(final Block block, final Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void verifyComponentsAfterLoad(final ItemStack itemStack)
    {
        super.verifyComponentsAfterLoad(itemStack);
        upgrade(itemStack);
    }

    static void upgrade(final ItemStack itemStack)
    {
        CustomData.update(DataComponents.CUSTOM_DATA, itemStack, oldData -> {    
            // move Type from root to BlockStateTag
            if (oldData.contains(TYPE_BLOCK_PROPERTY, Tag.TAG_STRING))
            {
                itemStack.update(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY, props -> with(props, TYPE_BLOCK_PROPERTY, oldData.getString(TYPE_BLOCK_PROPERTY)));
                oldData.remove(TYPE_BLOCK_PROPERTY);
            }
    
            // move TextureData from root to BlockEntityTag
            if (oldData.contains(BLOCK_ENTITY_TEXTURE_DATA, Tag.TAG_COMPOUND))
            {
                CustomData.update(DataComponents.BLOCK_ENTITY_DATA, itemStack, beData -> beData.put(BLOCK_ENTITY_TEXTURE_DATA, oldData.getCompound(BLOCK_ENTITY_TEXTURE_DATA)));
    
                oldData.remove(BLOCK_ENTITY_TEXTURE_DATA);
            }
        });
    }

    private static BlockItemStateProperties with(BlockItemStateProperties properties, String key, String value)
    {
        return new BlockItemStateProperties(Util.copyAndPut(properties.properties(), key, value));
    }
}