package com.ldtteam.domumornamentum.block.interfaces;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public interface IDOBlock<B extends IDOBlock<B>>
{
    /**
     * Registery block at gameregistry.
     *
     * @param registry the registry to use.
     * @return the block itself.
     */
    B registerBlock(final IForgeRegistry<Block> registry);

    /**
     * Registery block at gameregistry.
     *
     * @param registry the registry to use.
     */
    void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties);
}
