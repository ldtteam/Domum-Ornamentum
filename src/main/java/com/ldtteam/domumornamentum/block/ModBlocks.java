package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.decorative.*;
import com.ldtteam.domumornamentum.block.types.BrickType;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.block.vanilla.*;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
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
public final class ModBlocks implements IModBlocks
{
    private static       ArchitectsCutterBlock  ARCHITECTS_CUTTER;
    private static final List<TimberFrameBlock> TIMBER_FRAMES = Lists.newArrayList();
    private static       ShingleBlock           SHINGLE;
    private static       ShingleSlabBlock       SHINGLE_SLAB;
    private static       PaperWallBlock         PAPER_WALL;
    private static final List<ExtraBlock> EXTRA_TOP_BLOCKS = Lists.newArrayList();
    private static final List<FloatingCarpetBlock> FLOATING_CARPETS = Lists.newArrayList();
    private static       BarrelBlock STANDING_BARREL;
    private static       BarrelBlock      LAYING_BARREL;
    private static final List<BrickBlock> BRICK = new ArrayList<>();

    private static FenceBlock     FENCE;
    private static FenceGateBlock FENCE_GATE;
    private static SlabBlock SLAB;
    private static WallBlock WALL;
    private static StairBlock STAIR;
    private static TrapdoorBlock TRAPDOOR;
    private static DoorBlock  DOOR;
    private static PanelBlock STATIC_TRAPDOOR;

    private static FancyDoorBlock FANCY_DOOR;
    private static FancyTrapdoorBlock FANCY_TRAPDOOR;

    private static final ModBlocks INSTANCE = new ModBlocks();

    public static ModBlocks getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModBlocks()
    {
    }

    @Override
    public ArchitectsCutterBlock getArchitectsCutter()
    {
        return ModBlocks.ARCHITECTS_CUTTER;
    }

    @Override
    public ShingleBlock getShingle()
    {
        return ModBlocks.SHINGLE;
    }

    @Override
    public List<TimberFrameBlock> getTimberFrames()
    {
        return new ArrayList<>(ModBlocks.TIMBER_FRAMES);
    }

    @Override
    public ShingleSlabBlock getShingleSlab()
    {
        return ModBlocks.SHINGLE_SLAB;
    }

    @Override
    public PaperWallBlock getPaperWall()
    {
        return ModBlocks.PAPER_WALL;
    }

    @Override
    public List<ExtraBlock> getExtraTopBlocks()
    {
        return ModBlocks.EXTRA_TOP_BLOCKS;
    }

    @Override
    public List<FloatingCarpetBlock> getFloatingCarpets()
    {
        return ModBlocks.FLOATING_CARPETS;
    }

    @Override
    public BarrelBlock getStandingBarrel()
    {
        return ModBlocks.STANDING_BARREL;
    }

    @Override
    public BarrelBlock getLayingBarrel()
    {
        return ModBlocks.LAYING_BARREL;
    }

    @Override
    public FenceBlock getFence()
    {
        return ModBlocks.FENCE;
    }

    @Override
    public FenceGateBlock getFenceGate() { return ModBlocks.FENCE_GATE; }

    @Override
    public SlabBlock getSlab()
    {
        return ModBlocks.SLAB;
    }

    @Override
    public List<BrickBlock> getBricks()
    {
        return ModBlocks.BRICK;
    }

    @Override
    public WallBlock getWall()
    {
        return ModBlocks.WALL;
    }

    @Override
    public StairBlock getStair()
    {
        return ModBlocks.STAIR;
    }

    @Override
    public TrapdoorBlock getTrapdoor()
    {
        return ModBlocks.TRAPDOOR;
    }

    @Override
    public PanelBlock getPanel()
    {
        return ModBlocks.STATIC_TRAPDOOR;
    }

    @Override
    public DoorBlock getDoor()
    {
        return ModBlocks.DOOR;
    }

    @Override
    public FancyDoorBlock getFancyDoor() { return ModBlocks.FANCY_DOOR; }

    @Override
    public FancyTrapdoorBlock getFancyTrapdoor() { return ModBlocks.FANCY_TRAPDOOR; }

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

        for (final ExtraBlockType blockType : ExtraBlockType.values())
        {
            EXTRA_TOP_BLOCKS.add(new ExtraBlock(blockType).registerBlock(registry));
        }

        for (final DyeColor value : DyeColor.values())
        {
            FLOATING_CARPETS.add(new FloatingCarpetBlock(value).registerBlock(registry));
        }

        for (final BrickType type : BrickType.values())
        {
            BRICK.add(new BrickBlock(type).registerBlock(registry));
        }

        STANDING_BARREL = new BarrelBlock(true).registerBlock(registry);
        LAYING_BARREL = new BarrelBlock(false).registerBlock(registry);

        FENCE = new FenceBlock().registerBlock(registry);
        FENCE_GATE = new FenceGateBlock().registerBlock(registry);
        SLAB = new SlabBlock().registerBlock(registry);
        WALL = new WallBlock().registerBlock(registry);
        STAIR = new StairBlock().registerBlock(registry);
        TRAPDOOR = new TrapdoorBlock().registerBlock(registry);
        DOOR = new DoorBlock().registerBlock(registry);
        STATIC_TRAPDOOR = new PanelBlock().registerBlock(registry);

        FANCY_DOOR = new FancyDoorBlock().registerBlock(registry);
        FANCY_TRAPDOOR = new FancyTrapdoorBlock().registerBlock(registry);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();
        ARCHITECTS_CUTTER.registerItemBlock(registry, (new Item.Properties().tab(ModCreativeTabs.GENERAL)));

        final Item.Properties timberframeProperties = new Item.Properties().tab(ModCreativeTabs.GENERAL);

        for (final TimberFrameBlock frame : TIMBER_FRAMES)
        {
            frame.registerItemBlock(registry, timberframeProperties);
        }

        SHINGLE.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        SHINGLE_SLAB.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        PAPER_WALL.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));

        for (final ExtraBlock block : EXTRA_TOP_BLOCKS)
        {
            block.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.EXTRA_BLOCKS));
        }

        for (final FloatingCarpetBlock floatingCarpet : FLOATING_CARPETS)
        {
            floatingCarpet.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.FLOATING_CARPETS));
        }

        for (final BrickBlock brickBlock : BRICK)
        {
            brickBlock.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.EXTRA_BLOCKS));
        }

        STANDING_BARREL.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.EXTRA_BLOCKS));
        LAYING_BARREL.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.EXTRA_BLOCKS));

        FENCE.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        FENCE_GATE.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        SLAB.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        WALL.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        STAIR.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        TRAPDOOR.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        DOOR.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        STATIC_TRAPDOOR.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        FANCY_DOOR.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));
        FANCY_TRAPDOOR.registerItemBlock(registry, new Item.Properties().tab(ModCreativeTabs.GENERAL));

        ModRecipeTypes.init();
    }
}
