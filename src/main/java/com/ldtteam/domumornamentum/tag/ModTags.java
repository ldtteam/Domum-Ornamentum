package com.ldtteam.domumornamentum.tag;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class ModTags
{

    private ModTags()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModTags. This is a utility class");
    }

    public static final Tags.IOptionalNamedTag<Block> TIMBERFRAMES_FRAME = tag("timber_frames_frame");
    public static final Tags.IOptionalNamedTag<Block> TIMBERFRAMES_CENTER = tag("timber_frames_center");

    public static final Tags.IOptionalNamedTag<Block> SHINGLES_ROOF    = tag("shingles_roof");
    public static final Tags.IOptionalNamedTag<Block> SHINGLES_SUPPORT = tag("shingles_support");
    public static final Tags.IOptionalNamedTag<Block> SHINGLES_COVER = tag("shingles_cover");

    public static final Tags.IOptionalNamedTag<Block> PAPERWALL_FRAME = tag("paper_wall_frame");
    public static final Tags.IOptionalNamedTag<Block> PAPERWALL_CENTER = tag("paper_wall_center");

    public static final Tags.IOptionalNamedTag<Block> EXTRA_BLOCKS    = tag("extra_block");


    private static Tags.IOptionalNamedTag<Block> tag(String name)
    {
        return BlockTags.createOptional(new ResourceLocation(Constants.MOD_ID, name));
    }
}
