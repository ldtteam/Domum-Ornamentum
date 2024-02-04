package com.ldtteam.domumornamentum.block.interfaces;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public interface IDOBlock<B extends IDOBlock<B>>
{
    default ResourceLocation getRegistryName(final Block block)
    {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    ResourceLocation getRegistryName();
}
