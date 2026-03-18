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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.*;
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

    private static final ModBlocks INSTANCE = new ModBlocks();

    private static final DeferredBlock<ArchitectsCutterBlock> ARCHITECTS_CUTTER;
    private static final DeferredBlock<ShingleBlock> SHINGLE;
    private static final DeferredBlock<ShingleBlock> SHINGLE_FLAT;
    private static final DeferredBlock<ShingleBlock> SHINGLE_FLAT_LOWER;
    private static final DeferredBlock<ShingleBlock> SHINGLE_STEEP;
    private static final DeferredBlock<ShingleBlock> SHINGLE_STEEP_LOWER;
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
    private static final DeferredBlock<PillarBlock>        ROUND_PILLAR;
    private static final DeferredBlock<PillarBlock>        VOXEL_PILLAR;
    private static final DeferredBlock<PillarBlock>        SQUARE_PILLAR;
    private static final DeferredBlock<AllBrickBlock>      ALL_BRICK;
    private static final DeferredBlock<AllBrickBlock>      ALL_BRICK_DARK;
    private static final DeferredBlock<AllBrickStairBlock> ALL_BRICK_STAIR;
    private static final DeferredBlock<AllBrickStairBlock> ALL_BRICK_DARK_STAIR;

    static {
        ARCHITECTS_CUTTER = registerSimpleBlockItem("architectscutter", ArchitectsCutterBlock::new);

        for (final TimberFrameType blockType : TimberFrameType.values()) {
            TIMBER_FRAMES.add(registerCustomBlockItem(blockType.getName(), () -> new TimberFrameBlock(blockType), b -> new TimberFrameBlockItem(b, new Item.Properties())));
        }
        DYNAMIC_TIMBER_FRAME = registerCustomBlockItem("dynamic_timberframe", () -> new DynamicTimberFrameBlock(), b -> new DynamicTimberFrameBlockItem(b, new Item.Properties()));

        SHINGLE = registerCustomBlockItem("shingle", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties()));
        SHINGLE_FLAT = registerCustomBlockItem("shingle_flat", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties()));
        SHINGLE_FLAT_LOWER = registerCustomBlockItem("shingle_flat_lower", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties()));
        SHINGLE_STEEP = registerCustomBlockItem("shingle_steep", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties()));
        SHINGLE_STEEP_LOWER = registerCustomBlockItem("shingle_steep_lower", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties()));

        SHINGLE_SLAB = registerCustomBlockItem("shingle_slab", ShingleSlabBlock::new, b -> new ShingleSlabBlockItem(b, new Item.Properties()));
        PAPER_WALL = registerCustomBlockItem("blockpaperwall", PaperWallBlock::new, b -> new PaperwallBlockItem(b, new Item.Properties()));
        TILED_PAPER_WALL = registerCustomBlockItem("blocktiledpaperwall", PaperWallBlock::new, b -> new PaperwallBlockItem(b, new Item.Properties()));

        ROUND_PILLAR = registerCustomBlockItem("blockpillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties()));
        VOXEL_PILLAR = registerCustomBlockItem("blockypillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties()));
        SQUARE_PILLAR = registerCustomBlockItem("squarepillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties()));

        for (final ExtraBlockType blockType : ExtraBlockType.values()) {
            EXTRA_TOP_BLOCKS.add(registerCustomBlockItem(blockType.getSerializedName(), () -> new ExtraBlock(blockType), b -> new ExtraBlockItem(b, new Item.Properties())));
        }

        for (final FramedLightType blockType : FramedLightType.values())
        {
            FRAMED_LIGHT.add(registerCustomBlockItem(blockType.getName(), () -> new FramedLightBlock(blockType), b -> new FramedLightBlockItem(b, new Item.Properties())));
        }

        for (final DyeColor color : DyeColor.values()) {
            FLOATING_CARPETS.add(registerSimpleBlockItem(color.getName().toLowerCase(Locale.ROOT) + "_floating_carpet", () -> new FloatingCarpetBlock(color)));
        }

        for (final BrickType type : BrickType.values()) {
            BRICK.add(registerSimpleBlockItem(type.getSerializedName(), () -> new BrickBlock(type)));
        }

        STANDING_BARREL = registerSimpleBlockItem("blockbarreldeco_standing", BarrelBlock::new);
        LAYING_BARREL = registerSimpleBlockItem("blockbarreldeco_onside", BarrelBlock::new);

        FENCE = registerCustomBlockItem("vanilla_fence_compat", FenceBlock::new, b -> new FenceBlockItem(b, new Item.Properties()));
        FENCE_GATE = registerCustomBlockItem("vanilla_fence_gate_compat", FenceGateBlock::new, b -> new FenceGateBlockItem(b, new Item.Properties()));
        SLAB = registerCustomBlockItem("vanilla_slab_compat", SlabBlock::new, b -> new SlabBlockItem(b, new Item.Properties()));
        WALL = registerCustomBlockItem("vanilla_wall_compat", WallBlock::new, b -> new WallBlockItem(b, new Item.Properties()));
        STAIR = registerCustomBlockItem("vanilla_stairs_compat", StairBlock::new, b -> new StairsBlockItem(b, new Item.Properties()));
        TRAPDOOR = registerCustomBlockItem("vanilla_trapdoors_compat", TrapdoorBlock::new, b -> new TrapdoorBlockItem(b, new Item.Properties()));
        DOOR = registerCustomBlockItem("vanilla_doors_compat", DoorBlock::new, b -> new DoorBlockItem(b, new Item.Properties()));
        PANEL = registerCustomBlockItem("panel", PanelBlock::new, b -> new PanelBlockItem(b, new Item.Properties()));
        ALL_BRICK = (registerCustomBlockItem("light_brick", AllBrickBlock::new, b -> new AllBrickBlockItem(b, new Item.Properties())));
        ALL_BRICK_DARK = (registerCustomBlockItem("dark_brick", AllBrickBlock::new, b -> new AllBrickBlockItem(b, new Item.Properties())));
        ALL_BRICK_STAIR = (registerCustomBlockItem("light_brick_stair", AllBrickStairBlock::new, b -> new AllBrickStairBlockItem(b, new Item.Properties())));
        ALL_BRICK_DARK_STAIR = (registerCustomBlockItem("dark_brick_stair", AllBrickStairBlock::new, b -> new AllBrickStairBlockItem(b, new Item.Properties())));

        POST = registerCustomBlockItem("post", PostBlock::new, b -> new PostBlockItem(b, new Item.Properties()));

        FANCY_DOOR = registerCustomBlockItem("fancy_door", FancyDoorBlock::new, b -> new FancyDoorBlockItem(b, new Item.Properties()));
        FANCY_TRAPDOOR = registerCustomBlockItem("fancy_trapdoors", FancyTrapdoorBlock::new, b -> new FancyTrapdoorBlockItem(b, new Item.Properties()));
    }

    /**
     * Specific item groups.
     */
    public Map<ResourceLocation, List<ItemStack>> itemGroups = new LinkedHashMap<>();

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
        final DeferredBlock<B> registered = BLOCKS.register(name, block);
        ITEMS.registerSimpleBlockItem(registered);
        return registered;
    }

    public static <B extends Block> DeferredBlock<B> registerCustomBlockItem(String name, Supplier<B> block, Function<B, ? extends BlockItem> item)
    {
        final DeferredBlock<B> registered = BLOCKS.register(name, block);

        // inline of ITEMS.registerSimpleBlockItem(registered);
        ITEMS.register(registered.unwrapKey().orElseThrow().location().getPath(), key -> item.apply(registered.value()));

        return registered;
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
            case STEEP -> ModBlocks.SHINGLE_STEEP.get();
            case STEEP_LOWER -> ModBlocks.SHINGLE_STEEP_LOWER.get();
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
        return List.of(VOXEL_PILLAR.get(), ROUND_PILLAR.get(), SQUARE_PILLAR.get());
    }

    @Override
    public PillarBlock getRoundPillar()
    {
        return ROUND_PILLAR.get();
    }

    @Override
    public PillarBlock getVoxelPillar()
    {
        return VOXEL_PILLAR.get();
    }

    @Override
    public PillarBlock getSqaurePillar()
    {
        return SQUARE_PILLAR.get();
    }

    @Override
    public AllBrickBlock getAllBrickBlock()
    {
        return ALL_BRICK.get();
    }

    @Override
    public AllBrickBlock getAllBrickDarkBlock()
    {
        return ALL_BRICK_DARK.get();
    }

    @Override
    public AllBrickStairBlock getAllBrickStairBlock()
    {
        return ALL_BRICK_STAIR.get();
    }

    @Override
    public AllBrickStairBlock getAllBrickDarkStairBlock()
    {
        return ALL_BRICK_DARK_STAIR.get();
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
        return List.of(ALL_BRICK.get(), ALL_BRICK_DARK.get());
    }

    @Override
    public List<AllBrickStairBlock> getAllBrickStairBlocks() {
        return List.of(ALL_BRICK_STAIR.get(), ALL_BRICK_DARK_STAIR.get());
    }

    @Override
    public DynamicTimberFrameBlock getDynamicTimberFrame() {
        return ModBlocks.DYNAMIC_TIMBER_FRAME.get();
    }

    /**
     * Get or compute the item group specifics.
     * @return the item group.
     */
    public Map<ResourceLocation, List<ItemStack>> getOrComputeItemGroups()
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

            final Map<ResourceLocation, List<ItemStack>> itemGroupMap = itemGroups;
            List<ResourceLocation> ids = new ArrayList<>(itemGroupMap.keySet());
            SortedBlocks.sortGroups(ids);
            itemGroups = new LinkedHashMap<>();
            for (final ResourceLocation id : ids)
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
