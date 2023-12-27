package com.ldtteam.domumornamentum.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import static com.ldtteam.domumornamentum.util.Constants.BLOCK_ENTITY_TEXTURE_DATA;

/**
 * BlockItem with own DFU on tag load/set
 */
public class SelfUpgradingDoubleHighBlockItem extends BlockItem
{
    public SelfUpgradingDoubleHighBlockItem(final Block block, final Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void verifyTagAfterLoad(final CompoundTag tag)
    {
        super.verifyTagAfterLoad(tag);

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