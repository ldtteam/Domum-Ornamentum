package com.ldtteam.domumornamentum.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.tags.Tag;
import net.minecraft.resources.ResourceLocation;

public interface IMateriallyTexturedBlockComponent
{
    ResourceLocation getId();

    Tag<Block> getValidSkins();

    Block getDefault();

    boolean isOptional();
}
