package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.decorative.*;
import com.ldtteam.domumornamentum.block.types.ExtraShingleTopType;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.Item;
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

    private static       ArchitectsCutterBlock  ARCHITECTS_CUTTER;
    private static final List<TimberFrameBlock> TIMBER_FRAMES = Lists.newArrayList();
    private static       ShingleBlock           SHINGLE;
    private static       ShingleSlabBlock       SHINGLE_SLAB;
    private static       PaperWallBlock         PAPER_WALL;

    private static final List<ExtraBlock> EXTRA_TOP_BLOCKS = Lists.newArrayList();

    public static ArchitectsCutterBlock getArchitectsCutter()
    {
        return ARCHITECTS_CUTTER;
    }

    public static ShingleBlock getShingle()
    {
        return SHINGLE;
    }

    public static List<TimberFrameBlock> getTimberFrames()
    {
        return new ArrayList<>(TIMBER_FRAMES);
    }

    public static ShingleSlabBlock getShingleSlab()
    {
        return SHINGLE_SLAB;
    }

    public static PaperWallBlock getPaperWall()
    {
        return PAPER_WALL;
    }

    public static List<ExtraBlock> getExtraTopBlocks()
    {
        return EXTRA_TOP_BLOCKS;
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
        ARCHITECTS_CUTTER = new ArchitectsCutterBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F)).registerBlock(registry);

        for (final TimberFrameType blockType : TimberFrameType.values())
        {
            TIMBER_FRAMES.add(new TimberFrameBlock(blockType).registerBlock(registry));
        }

        SHINGLE = new ShingleBlock().registerBlock(registry);
        SHINGLE_SLAB = new ShingleSlabBlock().registerBlock(registry);
        PAPER_WALL = new PaperWallBlock().registerBlock(registry);

        for (final ExtraShingleTopType blockType : ExtraShingleTopType.values())
        {
            EXTRA_TOP_BLOCKS.add(new ExtraBlock(blockType).registerBlock(registry));
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();
        ARCHITECTS_CUTTER.registerItemBlock(registry, (new Item.Properties()).tab(ModCreativeTabs.GENERAL));

        final Item.Properties timberframeProperties = new Item.Properties().tab(ModCreativeTabs.TIMBER_FRAMES);

        for (final TimberFrameBlock frame : TIMBER_FRAMES)
        {
            frame.registerItemBlock(registry, timberframeProperties);
        }

        SHINGLE.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.SHINGLES));
        SHINGLE_SLAB.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.SHINGLE_SLABS));
        PAPER_WALL.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.PAPERWALLS));

        for (final ExtraBlock block : EXTRA_TOP_BLOCKS)
        {
            block.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        }
    }
}
