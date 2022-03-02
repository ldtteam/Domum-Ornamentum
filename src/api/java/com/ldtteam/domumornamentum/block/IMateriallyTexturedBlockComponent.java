package com.ldtteam.domumornamentum.block;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;

public interface IMateriallyTexturedBlockComponent
{
    ResourceLocation getId();

    TagKey<Block> getValidSkins();

    Block getDefault();

    boolean isOptional();
}
