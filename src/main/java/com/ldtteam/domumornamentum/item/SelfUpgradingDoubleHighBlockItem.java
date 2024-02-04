package com.ldtteam.domumornamentum.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.level.block.Block;

import static com.ldtteam.domumornamentum.util.Constants.BLOCK_ENTITY_TEXTURE_DATA;
import static com.ldtteam.domumornamentum.util.Constants.TYPE_BLOCK_PROPERTY;

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
    public void verifyTagAfterLoad(final CompoundTag tag)
    {
        super.verifyTagAfterLoad(tag);

        // move Type from root to BlockStateTag
        if (tag.contains(TYPE_BLOCK_PROPERTY, Tag.TAG_STRING))
        {
            final CompoundTag blockStateTag = tag.getCompound(BLOCK_STATE_TAG);
            tag.put(BLOCK_STATE_TAG, blockStateTag);

            blockStateTag.putString(TYPE_BLOCK_PROPERTY, tag.getString(TYPE_BLOCK_PROPERTY));
            tag.remove(TYPE_BLOCK_PROPERTY);
        }

        // move TextureData from root to BlockEntityTag
        if (tag.contains(BLOCK_ENTITY_TEXTURE_DATA, Tag.TAG_COMPOUND))
        {
            final CompoundTag blockEntityTag = tag.getCompound(BLOCK_ENTITY_TAG);
            tag.put(BLOCK_ENTITY_TAG, blockEntityTag);

            blockEntityTag.put(BLOCK_ENTITY_TEXTURE_DATA, tag.getCompound(BLOCK_ENTITY_TEXTURE_DATA));
            tag.remove(BLOCK_ENTITY_TEXTURE_DATA);
        }
    }
}