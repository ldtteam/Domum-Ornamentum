package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.decorative.*;
import com.ldtteam.domumornamentum.block.types.BrickType;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import com.ldtteam.domumornamentum.block.types.FramedLightType;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.block.vanilla.*;
import com.ldtteam.domumornamentum.item.decoration.*;
import com.ldtteam.domumornamentum.item.vanilla.*;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class to create the modBlocks.
 * References to the blocks can be made here
 * <p>
 * We disabled the following finals since we are neither able to mark the items as final, nor do we want to provide public accessors.
 */
@SuppressWarnings({"squid:ClassVariableVisibilityCheck", "squid:S2444", "squid:S1444", "squid:S1820",})
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks implements IModBlocks {
    /**
     * The deferred registry.
     */
    public final static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    public final static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    private static final List<RegistryObject<TimberFrameBlock>> TIMBER_FRAMES = Lists.newArrayList();
    private static final List<RegistryObject<FramedLightBlock>> FRAMED_LIGHT = Lists.newArrayList();
    private static final List<RegistryObject<FloatingCarpetBlock>> FLOATING_CARPETS = Lists.newArrayList();
    private static final List<RegistryObject<ExtraBlock>> EXTRA_TOP_BLOCKS = Lists.newArrayList();
    private static final List<RegistryObject<BrickBlock>> BRICK = new ArrayList<>();
    private static final List<RegistryObject<PillarBlock>> PILLARS = new ArrayList<>();
    private static final ModBlocks INSTANCE = new ModBlocks();
    /**
     * All the modblocks.
     */
    private static RegistryObject<ArchitectsCutterBlock> ARCHITECTS_CUTTER;
    private static RegistryObject<ShingleBlock> SHINGLE;
    private static RegistryObject<ShingleSlabBlock> SHINGLE_SLAB;
    private static RegistryObject<PaperWallBlock> PAPER_WALL;
    private static RegistryObject<BarrelBlock> STANDING_BARREL;
    private static RegistryObject<BarrelBlock> LAYING_BARREL;
    private static RegistryObject<FenceBlock> FENCE;
    private static RegistryObject<FenceGateBlock> FENCE_GATE;
    private static RegistryObject<SlabBlock> SLAB;
    private static RegistryObject<WallBlock> WALL;
    private static RegistryObject<StairBlock> STAIR;
    private static RegistryObject<TrapdoorBlock> TRAPDOOR;
    private static RegistryObject<DoorBlock> DOOR;
    private static RegistryObject<PanelBlock> STATIC_TRAPDOOR;
    private static RegistryObject<FancyDoorBlock> FANCY_DOOR;
    private static RegistryObject<FancyTrapdoorBlock> FANCY_TRAPDOOR;

    static {
        ARCHITECTS_CUTTER = register("architectscutter", ArchitectsCutterBlock::new, b -> new BlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));

        for (final TimberFrameType blockType : TimberFrameType.values()) {
            TIMBER_FRAMES.add(register(blockType.getName(), () -> new TimberFrameBlock(blockType), b -> new TimberFrameBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL))));
        }

        SHINGLE = register("shingle", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        SHINGLE_SLAB = register("shingle_slab", ShingleSlabBlock::new, b -> new ShingleSlabBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        PAPER_WALL = register("blockpaperwall", PaperWallBlock::new, b -> new PaperwallBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));

        PILLARS.add(register("blockpillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL))));
        PILLARS.add(register("blockypillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL))));
        PILLARS.add(register("squarepillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL))));

        for (final ExtraBlockType blockType : ExtraBlockType.values()) {
            EXTRA_TOP_BLOCKS.add(register(blockType.getSerializedName(), () -> new ExtraBlock(blockType), b -> new ExtraBlockItem(b, new Item.Properties().tab(ModCreativeTabs.EXTRA_BLOCKS))));
        }

        for (final FramedLightType blockType : FramedLightType.values())
        {
            FRAMED_LIGHT.add(register(blockType.getName(), () -> new FramedLightBlock(blockType), b -> new FramedLightBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL))));
        }

        for (final DyeColor color : DyeColor.values()) {
            FLOATING_CARPETS.add(register(color.getName().toLowerCase(Locale.ROOT) + "_floating_carpet", () -> new FloatingCarpetBlock(color), b -> new BlockItem(b, new Item.Properties().tab(ModCreativeTabs.FLOATING_CARPETS))));
        }

        for (final BrickType type : BrickType.values()) {
            BRICK.add(register(type.getSerializedName(), () -> new BrickBlock(type), b -> new BlockItem(b, new Item.Properties().tab(ModCreativeTabs.EXTRA_BLOCKS))));
        }

        STANDING_BARREL = register("blockbarreldeco_standing", BarrelBlock::new, b -> new BlockItem(b, new Item.Properties().tab(ModCreativeTabs.EXTRA_BLOCKS)));
        LAYING_BARREL = register("blockbarreldeco_onside", BarrelBlock::new, b -> new BlockItem(b, new Item.Properties().tab(ModCreativeTabs.EXTRA_BLOCKS)));

        FENCE = register("vanilla_fence_compat", FenceBlock::new, b -> new FenceBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        FENCE_GATE = register("vanilla_fence_gate_compat", FenceGateBlock::new, b -> new FenceGateBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        SLAB = register("vanilla_slab_compat", SlabBlock::new, b -> new SlabBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        WALL = register("vanilla_wall_compat", WallBlock::new, b -> new WallBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        STAIR = register("vanilla_stairs_compat", StairBlock::new, b -> new StairsBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        TRAPDOOR = register("vanilla_trapdoors_compat", TrapdoorBlock::new, b -> new TrapdoorBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        DOOR = register("vanilla_doors_compat", DoorBlock::new, b -> new DoorBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        STATIC_TRAPDOOR = register("panel", PanelBlock::new, b -> new PanelBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));

        FANCY_DOOR = register("fancy_door", FancyDoorBlock::new, b -> new FancyDoorBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
        FANCY_TRAPDOOR = register("fancy_trapdoors", FancyTrapdoorBlock::new, b -> new FancyTrapdoorBlockItem(b, new Item.Properties().tab(ModCreativeTabs.GENERAL)));
    }

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModBlocks() {
    }

    public static ModBlocks getInstance() {
        return INSTANCE;
    }

    /**
     * Utility shorthand to register blocks using the deferred registry.
     * Register item block together.
     *
     * @param name  the registry name of the block
     * @param block a factory / constructor to create the block on demand
     * @param <B>   the block subclass for the factory response
     * @return the block entry saved to the registry
     */
    public static <B extends Block, I extends Item> RegistryObject<B> register(String name, Supplier<B> block, Function<B, I> item) {
        RegistryObject<B> registered = BLOCKS.register(name.toLowerCase(), block);
        ITEMS.register(name.toLowerCase(), () -> item.apply(registered.get()));
        return registered;
    }

    @Override
    public ArchitectsCutterBlock getArchitectsCutter() {
        return ModBlocks.ARCHITECTS_CUTTER.get();
    }

    @Override
    public ShingleBlock getShingle() {
        return ModBlocks.SHINGLE.get();
    }

    @Override
    public List<TimberFrameBlock> getTimberFrames() {
        return ModBlocks.TIMBER_FRAMES.stream().map(RegistryObject::get).collect(Collectors.toList());
    }

    @Override
    public List<FramedLightBlock> getFramedLights()
    {
        return ModBlocks.FRAMED_LIGHT.stream().map(RegistryObject::get).collect(Collectors.toList());
    }

    @Override
    public List<PillarBlock> getPillars()
    {
        return ModBlocks.PILLARS.stream().map(RegistryObject::get).collect(Collectors.toList());
    }

    @Override
    public ShingleSlabBlock getShingleSlab() {
        return ModBlocks.SHINGLE_SLAB.get();
    }

    @Override
    public PaperWallBlock getPaperWall() {
        return ModBlocks.PAPER_WALL.get();
    }

    @Override
    public List<ExtraBlock> getExtraTopBlocks() {
        return ModBlocks.EXTRA_TOP_BLOCKS.stream().map(RegistryObject::get).toList();
    }

    @Override
    public List<FloatingCarpetBlock> getFloatingCarpets() {
        return ModBlocks.FLOATING_CARPETS.stream().map(RegistryObject::get).toList();
    }

    @Override
    public BarrelBlock getStandingBarrel() {
        return ModBlocks.STANDING_BARREL.get();
    }

    @Override
    public BarrelBlock getLayingBarrel() {
        return ModBlocks.LAYING_BARREL.get();
    }

    @Override
    public FenceBlock getFence() {
        return ModBlocks.FENCE.get();
    }

    @Override
    public FenceGateBlock getFenceGate() {
        return ModBlocks.FENCE_GATE.get();
    }

    @Override
    public SlabBlock getSlab() {
        return ModBlocks.SLAB.get();
    }

    @Override
    public List<BrickBlock> getBricks() {
        return ModBlocks.BRICK.stream().map(RegistryObject::get).toList();
    }

    @Override
    public WallBlock getWall() {
        return ModBlocks.WALL.get();
    }

    @Override
    public StairBlock getStair() {
        return ModBlocks.STAIR.get();
    }

    @Override
    public TrapdoorBlock getTrapdoor() {
        return ModBlocks.TRAPDOOR.get();
    }

    @Override
    public PanelBlock getPanel() {
        return ModBlocks.STATIC_TRAPDOOR.get();
    }

    @Override
    public DoorBlock getDoor() {
        return ModBlocks.DOOR.get();
    }

    @Override
    public FancyDoorBlock getFancyDoor() {
        return ModBlocks.FANCY_DOOR.get();
    }

    @Override
    public FancyTrapdoorBlock getFancyTrapdoor() {
        return ModBlocks.FANCY_TRAPDOOR.get();
    }
}
