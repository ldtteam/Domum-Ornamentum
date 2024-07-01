package com.ldtteam.domumornamentum.tag;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags
{

    private ModTags()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModTags. This is a utility class");
    }

    public static final TagKey<Block> TIMBERFRAMES_FRAME  = tag("timber_frames_frame");
    public static final TagKey<Block> TIMBERFRAMES_CENTER = tag("timber_frames_center");

    public static final TagKey<Block> FRAMED_LIGHT_CENTER = tag("framed_light_center");
    public static final TagKey<Block> SHINGLES_ROOF    = tag("shingles_roof");
    public static final TagKey<Block> SHINGLES_SUPPORT = tag("shingles_support");
    public static final TagKey<Block> SHINGLES_COVER = tag("shingles_cover");

    public static final TagKey<Block> PAPERWALL_FRAME = tag("paper_wall_frame");
    public static final TagKey<Block> PAPERWALL_CENTER = tag("paper_wall_center");

    public static final TagKey<Block> EXTRA_BLOCKS    = tag("extra_block");

    public static final TagKey<Block> FLOATING_CARPETS = tag("floating_carpet");

    public static final TagKey<Block> FENCE_MATERIALS = tag("fence_materials");
    public static final TagKey<Block> FENCE_GATE_MATERIALS = tag("fence_gate_materials");
    public static final TagKey<Block> SLAB_MATERIALS = tag("slab_materials");
    public static final TagKey<Block> WALL_MATERIALS = tag("wall_materials");
    public static final TagKey<Block> STAIRS_MATERIALS = tag("stairs_materials");
    public static final TagKey<Block> TRAPDOORS_MATERIALS = tag("trapdoors_materials");
    public static final TagKey<Block> FANCY_TRAPDOORS_MATERIALS = tag("fancy_trapdoors_materials");
    public static final TagKey<Block> POST_MATERIALS = tag("post_materials");
    public static final TagKey<Block> DOORS_MATERIALS = tag("doors_materials");

    public static final TagKey<Block> FANCY_DOORS_MATERIALS = tag("fancy_doors_materials");

    public static final TagKey<Block> BRICKS = tag("bricks");

    public static final TagKey<Block> CONCRETE = tag("concrete");
    public static final TagKey<Block> COPPER = tag("copper");

    public static final TagKey<Block> GLOBAL_DEFAULT = tag("default");
    public static final TagKey<Block> PILLAR_MATERIALS = tag("pillar_materials");
    public static final TagKey<Block> ALL_BRICK_MATERIALS = tag("all_brick_materials");
    public static final TagKey<Item> EXTRA_BLOCK_ITEMS = itemTag("extra_block_items");
    public static final TagKey<Item> BRICK_ITEMS       = itemTag("brick_items");

    public static final TagKey<Block> GLACED_TERRACOTTA = tag("glaced_terracotta");



    private static TagKey<Block> tag(String name)
    {
        return TagKey.create(Registries.BLOCK, Constants.resLocDO(name));
    }

    private static TagKey<Item> itemTag(String name)
    {
        return TagKey.create(Registries.ITEM, Constants.resLocDO(name));
    }

}
