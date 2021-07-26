package com.ldtteam.domumornamentum.block;

import java.util.function.Supplier;
import com.ldtteam.domumornamentum.util.Suppression;
import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public abstract class AbstractBlockStairs<B extends AbstractBlockStairs<B>> extends StairBlock implements IDOBlock<B>
{
    public AbstractBlockStairs(final Supplier<BlockState> state, final Properties properties)
    {
        super(state, properties);
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
