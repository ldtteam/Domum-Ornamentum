package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.decorative.*;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create the modBlocks.
 * References to the blocks can be made here
 * <p>
 * We disabled the following finals since we are neither able to mark the items as final, nor do we want to provide public accessors.
 */
@SuppressWarnings({"squid:ClassVariableVisibilityCheck", "squid:S2444", "squid:S1444", "squid:S1820",})

@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks
{

    private static List<TimberFrameBlock> timberFrames = Lists.newArrayList();
    private static BlockShingle           shingleBlock;

    public static BlockShingle getShingleBlock()
    {
        return shingleBlock;
    }

    public static List<TimberFrameBlock> getTimberFrames()
    {
        return new ArrayList<>(timberFrames);
    }

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModBlocks()
    {
        /**
         * Intentionally left empty.
         */
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        final IForgeRegistry<Block> registry = event.getRegistry();
        for (final TimberFrameType blockType : TimberFrameType.values())
        {
            timberFrames.add(new TimberFrameBlock(blockType).registerBlock(registry));
        }

        shingleBlock = new BlockShingle().registerBlock(registry);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();
        final Item.Properties timberframeProperties = new Item.Properties().group(ModCreativeTabs.TIMBER_FRAMES);

        for (final TimberFrameBlock frame : timberFrames)
        {
            frame.registerItemBlock(registry, timberframeProperties);
        }

        shingleBlock.registerItemBlock(registry, new Item.Properties().group(ModCreativeTabs.SHINGLES));
    }
}
