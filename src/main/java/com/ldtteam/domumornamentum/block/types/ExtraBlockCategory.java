package com.ldtteam.domumornamentum.block.types;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Represents a group, or category, of extra blocks.
 */
public enum ExtraBlockCategory
{
    BRICK(BlockTags.MINEABLE_WITH_PICKAXE),
    SLATE(BlockTags.MINEABLE_WITH_PICKAXE),
    THATCHED(BlockTags.MINEABLE_WITH_HOE),
    PAPER(BlockTags.MINEABLE_WITH_AXE),
    CACTUS(BlockTags.MINEABLE_WITH_AXE);

    /**
     * The mineable tag this extra block should be added to.
     */
    private final TagKey<Block> mineableTag;

    ExtraBlockCategory(final TagKey<Block> mineableTag)
    {
        this.mineableTag = mineableTag;
    }

    /**
     * The mineable tag for this extra block category.
     *
     * @return the tag key for the mineable type.
     */
    public TagKey<Block> getMineableTag()
    {
        return mineableTag;
    }
}
