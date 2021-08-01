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
    public static final Tags.IOptionalNamedTag<Block> FLOATING_CARPETS = tag("floating_carpet");

    public static final Tags.IOptionalNamedTag<Block> FENCE_MATERIALS = tag("fence_materials");
    public static final Tags.IOptionalNamedTag<Block> FENCE_GATE_MATERIALS = tag("fence_gate_materials");
    public static final Tags.IOptionalNamedTag<Block> SLAB_MATERIALS = tag("slab_materials");
    public static final Tags.IOptionalNamedTag<Block> WALL_MATERIALS = tag("wall_materials");
    public static final Tags.IOptionalNamedTag<Block> STAIRS_MATERIALS = tag("stairs_materials");
    public static final Tags.IOptionalNamedTag<Block> TRAPDOORS_MATERIALS = tag("trapdoors_materials");

    public static final Tags.IOptionalNamedTag<Block> BRICKS = tag("bricks");

    private static Tags.IOptionalNamedTag<Block> tag(String name)
    {
        return BlockTags.createOptional(new ResourceLocation(Constants.MOD_ID, name));
    }
}
