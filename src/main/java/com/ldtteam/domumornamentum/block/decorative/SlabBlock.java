package com.ldtteam.domumornamentum.block.decorative;

import com.ldtteam.domumornamentum.util.Suppression;
import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import net.minecraft.block.AbstractBlock.Properties;

public class SlabBlock<B extends SlabBlock<B>> extends net.minecraft.block.SlabBlock implements IDOBlock<B>
{
    /**
     * Constructor of abstract class.
     * @param properties the input properties.
     */
    public SlabBlock(final Properties properties, final String registryName)
    {
        super(properties);
        this.setRegistryName(registryName);
    }

    @Override
    @SuppressWarnings(Suppression.UNCHECKED)
    public B registerBlock(final IForgeRegistry<Block> registry)
    {
        registry.register(this);
        return (B) this;
    }

    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new BlockItem(this, properties)).setRegistryName(this.getRegistryName()));
    }
}
