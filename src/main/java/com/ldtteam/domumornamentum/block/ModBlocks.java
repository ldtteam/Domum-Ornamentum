package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.block.decorative.*;
import com.ldtteam.domumornamentum.block.types.BrickType;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import com.ldtteam.domumornamentum.block.types.FramedLightType;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.block.vanilla.*;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.item.decoration.*;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.item.vanilla.*;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class to create the modBlocks.
 * References to the blocks can be made here
 * <p>
 * We disabled the following finals since we are neither able to mark the items as final, nor do we want to provide public accessors.
 */
@SuppressWarnings({"squid:ClassVariableVisibilityCheck", "squid:S2444", "squid:S1444", "squid:S1820",})
public final class ModBlocks implements IModBlocks {
    /**
     * The deferred registry.
     */
    public final static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
    public final static DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);

    private static final List<Supplier<TimberFrameBlock>> TIMBER_FRAMES = new ArrayList<>();
    private static final List<Supplier<FramedLightBlock>> FRAMED_LIGHT = new ArrayList<>();
    private static final List<Supplier<FloatingCarpetBlock>> FLOATING_CARPETS = new ArrayList<>();
    private static final List<Supplier<ExtraBlock>> EXTRA_TOP_BLOCKS = new ArrayList<>();
    private static final List<Supplier<BrickBlock>> BRICK = new ArrayList<>();
    private static final List<DeferredBlock<PillarBlock>> PILLARS = new ArrayList<>();
    private static final List<DeferredBlock<AllBrickBlock>> ALL_BRICK = new ArrayList<>();
    private static final List<DeferredBlock<AllBrickStairBlock>> ALL_BRICK_STAIR = new ArrayList<>();

    private static final ModBlocks INSTANCE = new ModBlocks();

    private static final DeferredBlock<ArchitectsCutterBlock> ARCHITECTS_CUTTER;
    private static final DeferredBlock<ShingleBlock> SHINGLE;
    private static final DeferredBlock<ShingleBlock> SHINGLE_FLAT;
    private static final DeferredBlock<ShingleBlock> SHINGLE_FLAT_LOWER;
    private static final DeferredBlock<ShingleSlabBlock> SHINGLE_SLAB;
    private static final DeferredBlock<PaperWallBlock> PAPER_WALL;
    private static final DeferredBlock<BarrelBlock> STANDING_BARREL;
    private static final DeferredBlock<BarrelBlock> LAYING_BARREL;
    private static final DeferredBlock<FenceBlock> FENCE;
    private static final DeferredBlock<FenceGateBlock> FENCE_GATE;
    private static final DeferredBlock<SlabBlock> SLAB;
    private static final DeferredBlock<WallBlock> WALL;
    private static final DeferredBlock<StairBlock> STAIR;
    private static final DeferredBlock<TrapdoorBlock> TRAPDOOR;
    private static final DeferredBlock<DoorBlock> DOOR;
    private static final DeferredBlock<PostBlock> POST;
    private static final DeferredBlock<PanelBlock> PANEL;
    private static final DeferredBlock<FancyDoorBlock> FANCY_DOOR;
    private static final DeferredBlock<FancyTrapdoorBlock> FANCY_TRAPDOOR;
    private static final DeferredBlock<PaperWallBlock> TILED_PAPER_WALL;
    private static final DeferredBlock<DynamicTimberFrameBlock> DYNAMIC_TIMBER_FRAME;

    static {
        ARCHITECTS_CUTTER = BLOCKS.registerBlock("architectscutter", ArchitectsCutterBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerSimpleBlockItem(ARCHITECTS_CUTTER);

        for (final TimberFrameType blockType : TimberFrameType.values()) {
            final DeferredBlock<TimberFrameBlock> registered = BLOCKS.registerBlock(
                blockType.getName(),
                properties -> new TimberFrameBlock(blockType, properties),
                BlockBehaviour.Properties::of
            );
            TIMBER_FRAMES.add(registered);
            ITEMS.registerItem(blockType.getName(), properties -> new TimberFrameBlockItem(registered.value(), properties), Item.Properties::new);
        }
        DYNAMIC_TIMBER_FRAME = BLOCKS.registerBlock(
            "dynamic_timberframe",
            DynamicTimberFrameBlock::new,
            BlockBehaviour.Properties::of
        );
        ITEMS.registerItem("dynamic_timberframe", properties -> new DynamicTimberFrameBlockItem(DYNAMIC_TIMBER_FRAME.value(), properties), Item.Properties::new);

        SHINGLE = BLOCKS.registerBlock("shingle", ShingleBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("shingle", properties -> new ShingleBlockItem(SHINGLE.value(), properties), Item.Properties::new);
        SHINGLE_FLAT = BLOCKS.registerBlock("shingle_flat", ShingleBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("shingle_flat", properties -> new ShingleBlockItem(SHINGLE_FLAT.value(), properties), Item.Properties::new);
        SHINGLE_FLAT_LOWER = BLOCKS.registerBlock("shingle_flat_lower", ShingleBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("shingle_flat_lower", properties -> new ShingleBlockItem(SHINGLE_FLAT_LOWER.value(), properties), Item.Properties::new);

        SHINGLE_SLAB = BLOCKS.registerBlock("shingle_slab", ShingleSlabBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("shingle_slab", properties -> new ShingleSlabBlockItem(SHINGLE_SLAB.value(), properties), Item.Properties::new);
        PAPER_WALL = BLOCKS.registerBlock("blockpaperwall", PaperWallBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("blockpaperwall", properties -> new PaperwallBlockItem(PAPER_WALL.value(), properties), Item.Properties::new);
        TILED_PAPER_WALL = BLOCKS.registerBlock("blocktiledpaperwall", PaperWallBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("blocktiledpaperwall", properties -> new PaperwallBlockItem(TILED_PAPER_WALL.value(), properties), Item.Properties::new);

        final DeferredBlock<PillarBlock> blockPillar = BLOCKS.registerBlock("blockpillar", PillarBlock::new, BlockBehaviour.Properties::of);
        PILLARS.add(blockPillar);
        ITEMS.registerItem("blockpillar", properties -> new PillarBlockItem(blockPillar.value(), properties), Item.Properties::new);
        final DeferredBlock<PillarBlock> blockYPillar = BLOCKS.registerBlock("blockypillar", PillarBlock::new, BlockBehaviour.Properties::of);
        PILLARS.add(blockYPillar);
        ITEMS.registerItem("blockypillar", properties -> new PillarBlockItem(blockYPillar.value(), properties), Item.Properties::new);
        final DeferredBlock<PillarBlock> squarePillar = BLOCKS.registerBlock("squarepillar", PillarBlock::new, BlockBehaviour.Properties::of);
        PILLARS.add(squarePillar);
        ITEMS.registerItem("squarepillar", properties -> new PillarBlockItem(squarePillar.value(), properties), Item.Properties::new);

        for (final ExtraBlockType blockType : ExtraBlockType.values()) {
            final DeferredBlock<ExtraBlock> registered = BLOCKS.registerBlock(
                blockType.getSerializedName(),
                properties -> new ExtraBlock(blockType, properties),
                BlockBehaviour.Properties::of
            );
            EXTRA_TOP_BLOCKS.add(registered);
            ITEMS.registerItem(blockType.getSerializedName(), properties -> new ExtraBlockItem(registered.value(), properties), Item.Properties::new);
        }

        for (final FramedLightType blockType : FramedLightType.values())
        {
            final DeferredBlock<FramedLightBlock> registered = BLOCKS.registerBlock(
                blockType.getName(),
                properties -> new FramedLightBlock(blockType, properties),
                BlockBehaviour.Properties::of
            );
            FRAMED_LIGHT.add(registered);
            ITEMS.registerItem(blockType.getName(), properties -> new FramedLightBlockItem(registered.value(), properties), Item.Properties::new);
        }

        for (final DyeColor color : DyeColor.values()) {
            final String name = color.getName().toLowerCase(Locale.ROOT) + "_floating_carpet";
            final DeferredBlock<FloatingCarpetBlock> registered = BLOCKS.registerBlock(
                name,
                properties -> new FloatingCarpetBlock(color, properties),
                BlockBehaviour.Properties::of
            );
            FLOATING_CARPETS.add(registered);
            ITEMS.registerSimpleBlockItem(registered);
        }

        for (final BrickType type : BrickType.values()) {
            final DeferredBlock<BrickBlock> registered = BLOCKS.registerBlock(
                type.getSerializedName(),
                properties -> new BrickBlock(type, properties),
                BlockBehaviour.Properties::of
            );
            BRICK.add(registered);
            ITEMS.registerSimpleBlockItem(registered);
        }

        STANDING_BARREL = BLOCKS.registerBlock(
            "blockbarreldeco_standing",
            BarrelBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS)
        );
        ITEMS.registerSimpleBlockItem(STANDING_BARREL);
        LAYING_BARREL = BLOCKS.registerBlock(
            "blockbarreldeco_onside",
            BarrelBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.OAK_PLANKS)
        );
        ITEMS.registerSimpleBlockItem(LAYING_BARREL);

        FENCE = BLOCKS.registerBlock("vanilla_fence_compat", FenceBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("vanilla_fence_compat", properties -> new FenceBlockItem(FENCE.value(), properties), Item.Properties::new);
        FENCE_GATE = BLOCKS.registerBlock("vanilla_fence_gate_compat", FenceGateBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("vanilla_fence_gate_compat", properties -> new FenceGateBlockItem(FENCE_GATE.value(), properties), Item.Properties::new);
        SLAB = BLOCKS.registerBlock("vanilla_slab_compat", SlabBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("vanilla_slab_compat", properties -> new SlabBlockItem(SLAB.value(), properties), Item.Properties::new);
        WALL = BLOCKS.registerBlock("vanilla_wall_compat", WallBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("vanilla_wall_compat", properties -> new WallBlockItem(WALL.value(), properties), Item.Properties::new);
        STAIR = BLOCKS.registerBlock("vanilla_stairs_compat", StairBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("vanilla_stairs_compat", properties -> new StairsBlockItem(STAIR.value(), properties), Item.Properties::new);
        TRAPDOOR = BLOCKS.registerBlock("vanilla_trapdoors_compat", TrapdoorBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("vanilla_trapdoors_compat", properties -> new TrapdoorBlockItem(TRAPDOOR.value(), properties), Item.Properties::new);
        DOOR = BLOCKS.registerBlock("vanilla_doors_compat", DoorBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("vanilla_doors_compat", properties -> new DoorBlockItem(DOOR.value(), properties), Item.Properties::new);
        PANEL = BLOCKS.registerBlock("panel", PanelBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("panel", properties -> new PanelBlockItem(PANEL.value(), properties), Item.Properties::new);
        final DeferredBlock<AllBrickBlock> lightBrick = BLOCKS.registerBlock("light_brick", AllBrickBlock::new, BlockBehaviour.Properties::of);
        ALL_BRICK.add(lightBrick);
        ITEMS.registerItem("light_brick", properties -> new AllBrickBlockItem(lightBrick.value(), properties), Item.Properties::new);
        final DeferredBlock<AllBrickBlock> darkBrick = BLOCKS.registerBlock("dark_brick", AllBrickBlock::new, BlockBehaviour.Properties::of);
        ALL_BRICK.add(darkBrick);
        ITEMS.registerItem("dark_brick", properties -> new AllBrickBlockItem(darkBrick.value(), properties), Item.Properties::new);
        final DeferredBlock<AllBrickStairBlock> lightBrickStair = BLOCKS.registerBlock("light_brick_stair", AllBrickStairBlock::new, BlockBehaviour.Properties::of);
        ALL_BRICK_STAIR.add(lightBrickStair);
        ITEMS.registerItem("light_brick_stair", properties -> new AllBrickStairBlockItem(lightBrickStair.value(), properties), Item.Properties::new);
        final DeferredBlock<AllBrickStairBlock> darkBrickStair = BLOCKS.registerBlock("dark_brick_stair", AllBrickStairBlock::new, BlockBehaviour.Properties::of);
        ALL_BRICK_STAIR.add(darkBrickStair);
        ITEMS.registerItem("dark_brick_stair", properties -> new AllBrickStairBlockItem(darkBrickStair.value(), properties), Item.Properties::new);

        POST = BLOCKS.registerBlock("post", PostBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("post", properties -> new PostBlockItem(POST.value(), properties), Item.Properties::new);

        FANCY_DOOR = BLOCKS.registerBlock("fancy_door", FancyDoorBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("fancy_door", properties -> new FancyDoorBlockItem(FANCY_DOOR.value(), properties), Item.Properties::new);
        FANCY_TRAPDOOR = BLOCKS.registerBlock("fancy_trapdoors", FancyTrapdoorBlock::new, BlockBehaviour.Properties::of);
        ITEMS.registerItem("fancy_trapdoors", properties -> new FancyTrapdoorBlockItem(FANCY_TRAPDOOR.value(), properties), Item.Properties::new);
    }

    /**
     * Specific item groups.
     */
    /**
     * The cutter relies on insertion order after applying {@link SortedBlocks}.
     * A sorted map would silently replace that presentation order with the
     * registry's lexical order whenever the 26.2 item registry is rebuilt.
     */
    public Map<Identifier, List<ItemStack>> itemGroups = new LinkedHashMap<>();

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
    public static <B extends Block> DeferredBlock<B> registerSimpleBlockItem(String name, Supplier<B> block)
    {
        throw new UnsupportedOperationException("Use BLOCKS.registerBlock so Minecraft 26 receives the block id");
    }

    public static <B extends Block> DeferredBlock<B> registerCustomBlockItem(
        String name,
        Supplier<B> block,
        BiFunction<B, Item.Properties, ? extends BlockItem> item
    )
    {
        throw new UnsupportedOperationException("Use BLOCKS.registerBlock and DeferredRegister.Items.registerItem");
    }

    @Override
    public ArchitectsCutterBlock getArchitectsCutter() {
        return ModBlocks.ARCHITECTS_CUTTER.get();
    }

    @Override
    public ShingleBlock getShingle(final ShingleHeightType heightType) {
        return switch (heightType)
        {
            case DEFAULT -> ModBlocks.SHINGLE.get();
            case FLAT -> ModBlocks.SHINGLE_FLAT.get();
            case FLAT_LOWER -> ModBlocks.SHINGLE_FLAT_LOWER.get();
        };
    }

    @Override
    public List<TimberFrameBlock> getTimberFrames() {
        return ModBlocks.TIMBER_FRAMES.stream().map(Supplier::get).collect(Collectors.toList());
    }

    @Override
    public List<FramedLightBlock> getFramedLights()
    {
        return ModBlocks.FRAMED_LIGHT.stream().map(Supplier::get).collect(Collectors.toList());
    }

    @Override
    public List<PillarBlock> getPillars()
    {
        return ModBlocks.PILLARS.stream().map(DeferredBlock::value).collect(Collectors.toList());
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
    public PaperWallBlock getTiledPaperWall() {
        return ModBlocks.TILED_PAPER_WALL.get();
    }

    @Override
    public List<ExtraBlock> getExtraTopBlocks() {
        return ModBlocks.EXTRA_TOP_BLOCKS.stream().map(Supplier::get).toList();
    }

    @Override
    public List<FloatingCarpetBlock> getFloatingCarpets() {
        return ModBlocks.FLOATING_CARPETS.stream().map(Supplier::get).toList();
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
        return ModBlocks.BRICK.stream().map(Supplier::get).toList();
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
        return ModBlocks.PANEL.get();
    }

    @Override
    public PostBlock getPost() {
        return ModBlocks.POST.get();
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

    @Override
    public List<AllBrickBlock> getAllBrickBlocks() {
        return ModBlocks.ALL_BRICK.stream().map(DeferredBlock::value).toList();
    }

    @Override
    public List<AllBrickStairBlock> getAllBrickStairBlocks() {
        return ModBlocks.ALL_BRICK_STAIR.stream().map(DeferredBlock::value).toList();
    }

    @Override
    public DynamicTimberFrameBlock getDynamicTimberFrame() {
        return ModBlocks.DYNAMIC_TIMBER_FRAME.get();
    }

    /**
     * Get or compute the item group specifics.
     * @return the item group.
     */
    public Map<Identifier, List<ItemStack>> getOrComputeItemGroups()
    {
        if (itemGroups.isEmpty())
        {
            BuiltInRegistries.ITEM.forEach(item -> {
                if (item instanceof IDoItem)
                {
                    final List<ItemStack> itemList = itemGroups.getOrDefault(((IDoItem) item).getGroup(), new ArrayList<>());
                    if (item instanceof BlockItem blockitem && blockitem.getBlock() instanceof IMateriallyTexturedBlock texturedBlock) {
                        if (blockitem.getBlock() instanceof ICachedItemGroupBlock cachedItemGroupBlock)
                        {
                            final NonNullList<ItemStack> stacks = NonNullList.create();
                            cachedItemGroupBlock.fillItemCategory(stacks);

                            for (final ItemStack stack : stacks)
                            {
                                itemList.add(process(stack.copy(), texturedBlock));
                            }
                        }
                        else
                        {
                            itemList.add(process(new ItemStack(item), texturedBlock));
                        }
                    }
                    itemGroups.put(((IDoItem) item).getGroup(), SortedBlocks.sortItems(itemList));
                }
            });

            final Map<Identifier, List<ItemStack>> itemGroupMap = itemGroups;
            final List<Identifier> ids = new ArrayList<>(itemGroupMap.keySet());
            SortedBlocks.sortGroups(ids);
            itemGroups = new LinkedHashMap<>();
            for (final Identifier id : ids)
            {
                itemGroups.put(id, itemGroupMap.get(id));
            }
        }
        return itemGroups;
    }

    private ItemStack process(final ItemStack stack, final IMateriallyTexturedBlock block)
    {
        final @NotNull List<IMateriallyTexturedBlockComponent> components = new ArrayList<>(block.getComponents());
        final MaterialTextureData.Builder textureData = MaterialTextureData.builder();

        for (final IMateriallyTexturedBlockComponent component : components)
        {
            textureData.setComponent(component.getId(), component.getDefault());
        }

        textureData.writeToItemStack(stack);

        return stack;
    }

    public static Block[] getMateriallyTexturableBlocks() {
        return BLOCKS.getRegistry()
                .get()
                .stream()
                .filter(IMateriallyTexturedBlock.class::isInstance)
                .toArray(Block[]::new);
    }

    public static Item[] getMateriallyTexturableItems() {
        return Arrays.stream(getMateriallyTexturableBlocks())
                .map(block -> BLOCKS.getRegistry().get().getKey(block))
                .map(name -> ITEMS.getRegistry().get().get(name))
                .toArray(Item[]::new);
    }
}
