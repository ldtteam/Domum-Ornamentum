package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.util.Suppression;
import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AbstractBlockDirectional<B extends AbstractBlockDirectional<B>> extends HorizontalBlock implements IDOBlock<B>
{
    public AbstractBlockDirectional(final Properties properties)
    {
        super(properties);
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
