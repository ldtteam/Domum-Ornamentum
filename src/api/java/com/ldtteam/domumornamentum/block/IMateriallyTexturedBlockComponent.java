package com.ldtteam.domumornamentum.block;

import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public interface IMateriallyTexturedBlockComponent
{
    ResourceLocation getId();

    ITag<Block> getValidSkins();

    Block getDefault();
}
