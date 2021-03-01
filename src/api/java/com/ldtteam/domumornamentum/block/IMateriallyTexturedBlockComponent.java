package com.ldtteam.domumornamentum.block;

import net.minecraft.block.Block;
import net.minecraft.tags.ITag;

public interface IMateriallyTexturedBlockComponent
{

    public String getId();

    public ITag<Block> getValidSkins();
}
