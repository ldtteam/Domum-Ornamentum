package com.ldtteam.domumornamentum.block.interfaces;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public interface IDOBlock<B extends IDOBlock<B>>
{
    default ResourceLocation getRegistryName(final Block block)
    {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    ResourceLocation getRegistryName();
}
