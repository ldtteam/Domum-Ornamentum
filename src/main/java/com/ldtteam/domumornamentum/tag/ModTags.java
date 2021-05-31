package com.ldtteam.domumornamentum.tag;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags
{

    private ModTags()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModTags. This is a utility class");
    }

    public static final Tags.IOptionalNamedTag<Block> TIMBERFRAMES_FRAME = tag("timber_frames_frame");
    public static final Tags.IOptionalNamedTag<Block> TIMBERFRAMES_CENTER = tag("timber_frames_center");

    public static final Tags.IOptionalNamedTag<Block> SHINGLES_COVER = tag("shingles_cover");
    public static final Tags.IOptionalNamedTag<Block> SHINGLES_SUPPORT = tag("shingles_support");

    private static Tags.IOptionalNamedTag<Block> tag(String name)
    {
        return BlockTags.createOptional(new ResourceLocation(Constants.MOD_ID, name));
    }
}
