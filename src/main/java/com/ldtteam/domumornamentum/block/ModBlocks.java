package com.ldtteam.domumornamentum.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
    private static final List<RegistryObject<AllBrickBlock>> ALL_BRICK = new ArrayList<>();
    private static final List<RegistryObject<AllBrickStairBlock>> ALL_BRICK_STAIR = new ArrayList<>();

    private static final ModBlocks INSTANCE = new ModBlocks();

    private static final RegistryObject<ArchitectsCutterBlock> ARCHITECTS_CUTTER;
    private static final RegistryObject<ShingleBlock> SHINGLE;
    private static final RegistryObject<ShingleBlock> SHINGLE_FLAT;
    private static final RegistryObject<ShingleBlock> SHINGLE_FLAT_LOWER;
    private static final RegistryObject<ShingleSlabBlock> SHINGLE_SLAB;
    private static final RegistryObject<PaperWallBlock> PAPER_WALL;
    private static final RegistryObject<BarrelBlock> STANDING_BARREL;
    private static final RegistryObject<BarrelBlock> LAYING_BARREL;
    private static final RegistryObject<FenceBlock> FENCE;
    private static final RegistryObject<FenceGateBlock> FENCE_GATE;
    private static final RegistryObject<SlabBlock> SLAB;
    private static final RegistryObject<WallBlock> WALL;
    private static final RegistryObject<StairBlock> STAIR;
    private static final RegistryObject<TrapdoorBlock> TRAPDOOR;
    private static final RegistryObject<DoorBlock> DOOR;
    private static final RegistryObject<PostBlock> POST;
    private static final RegistryObject<PanelBlock> PANEL;
    private static final RegistryObject<FancyDoorBlock> FANCY_DOOR;
    private static final RegistryObject<FancyTrapdoorBlock> FANCY_TRAPDOOR;

    static {
        ARCHITECTS_CUTTER = register("architectscutter", ArchitectsCutterBlock::new, b -> new BlockItem(b, new Item.Properties()));

        for (final TimberFrameType blockType : TimberFrameType.values()) {
            TIMBER_FRAMES.add(register(blockType.getName(), () -> new TimberFrameBlock(blockType), b -> new TimberFrameBlockItem(b, new Item.Properties())));
        }

        SHINGLE = register("shingle", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties()));
        SHINGLE_FLAT = register("shingle_flat", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties()));
        SHINGLE_FLAT_LOWER = register("shingle_flat_lower", ShingleBlock::new, b -> new ShingleBlockItem(b, new Item.Properties()));

        SHINGLE_SLAB = register("shingle_slab", ShingleSlabBlock::new, b -> new ShingleSlabBlockItem(b, new Item.Properties()));
        PAPER_WALL = register("blockpaperwall", PaperWallBlock::new, b -> new PaperwallBlockItem(b, new Item.Properties()));

        PILLARS.add(register("blockpillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties())));
        PILLARS.add(register("blockypillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties())));
        PILLARS.add(register("squarepillar", PillarBlock::new, b -> new PillarBlockItem(b, new Item.Properties())));

        for (final ExtraBlockType blockType : ExtraBlockType.values()) {
            EXTRA_TOP_BLOCKS.add(register(blockType.getSerializedName(), () -> new ExtraBlock(blockType), b -> new ExtraBlockItem(b, new Item.Properties())));
        }

        for (final FramedLightType blockType : FramedLightType.values())
        {
            FRAMED_LIGHT.add(register(blockType.getName(), () -> new FramedLightBlock(blockType), b -> new FramedLightBlockItem(b, new Item.Properties())));
        }

        for (final DyeColor color : DyeColor.values()) {
            FLOATING_CARPETS.add(register(color.getName().toLowerCase(Locale.ROOT) + "_floating_carpet", () -> new FloatingCarpetBlock(color), b -> new BlockItem(b, new Item.Properties())));
        }

        for (final BrickType type : BrickType.values()) {
            BRICK.add(register(type.getSerializedName(), () -> new BrickBlock(type), b -> new BlockItem(b, new Item.Properties())));
        }

        STANDING_BARREL = register("blockbarreldeco_standing", BarrelBlock::new, b -> new BlockItem(b, new Item.Properties()));
        LAYING_BARREL = register("blockbarreldeco_onside", BarrelBlock::new, b -> new BlockItem(b, new Item.Properties()));

        FENCE = register("vanilla_fence_compat", FenceBlock::new, b -> new FenceBlockItem(b, new Item.Properties()));
        FENCE_GATE = register("vanilla_fence_gate_compat", FenceGateBlock::new, b -> new FenceGateBlockItem(b, new Item.Properties()));
        SLAB = register("vanilla_slab_compat", SlabBlock::new, b -> new SlabBlockItem(b, new Item.Properties()));
        WALL = register("vanilla_wall_compat", WallBlock::new, b -> new WallBlockItem(b, new Item.Properties()));
        STAIR = register("vanilla_stairs_compat", StairBlock::new, b -> new StairsBlockItem(b, new Item.Properties()));
        TRAPDOOR = register("vanilla_trapdoors_compat", TrapdoorBlock::new, b -> new TrapdoorBlockItem(b, new Item.Properties()));
        DOOR = register("vanilla_doors_compat", DoorBlock::new, b -> new DoorBlockItem(b, new Item.Properties()));
        PANEL = register("panel", PanelBlock::new, b -> new PanelBlockItem(b, new Item.Properties()));
        ALL_BRICK.add(register("light_brick", AllBrickBlock::new, b -> new AllBrickBlockItem(b, new Item.Properties())));
        ALL_BRICK.add(register("dark_brick", AllBrickBlock::new, b -> new AllBrickBlockItem(b, new Item.Properties())));
        ALL_BRICK_STAIR.add(register("light_brick_stair", AllBrickStairBlock::new, b -> new AllBrickStairBlockItem(b, new Item.Properties())));
        ALL_BRICK_STAIR.add(register("dark_brick_stair", AllBrickStairBlock::new, b -> new AllBrickStairBlockItem(b, new Item.Properties())));

        POST = register("post", PostBlock::new, b -> new PostBlockItem(b, new Item.Properties()));

        FANCY_DOOR = register("fancy_door", FancyDoorBlock::new, b -> new FancyDoorBlockItem(b, new Item.Properties()));
        FANCY_TRAPDOOR = register("fancy_trapdoors", FancyTrapdoorBlock::new, b -> new FancyTrapdoorBlockItem(b, new Item.Properties()));
    }

    /**
     * Specific item groups.
     */
    public Map<ResourceLocation, List<ItemStack>> itemGroups = new TreeMap<>();

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
        return ModBlocks.ALL_BRICK.stream().map(RegistryObject::get).toList();
    }

    @Override
    public List<AllBrickStairBlock> getAllBrickStairBlocks() {
        return ModBlocks.ALL_BRICK_STAIR.stream().map(RegistryObject::get).toList();
    }

    /**
     * Get or compute the item group specifics.
     * @return the item group.
     */
    public Map<ResourceLocation, List<ItemStack>> getOrComputeItemGroups()
    {
        if (itemGroups.isEmpty())
        {
            ForgeRegistries.ITEMS.forEach(item -> {
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
                    itemGroups.put(((IDoItem) item).getGroup(), itemList);
                }
            });
        }
        return itemGroups;
    }

    private ItemStack process(final ItemStack stack, final IMateriallyTexturedBlock block)
    {
        final @NotNull List<IMateriallyTexturedBlockComponent> components = new ArrayList<>(block.getComponents());
        final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

        for (final IMateriallyTexturedBlockComponent component : components)
        {
            textureData.put(component.getId(), component.getDefault());
        }

        new MaterialTextureData(textureData).writeToItemStack(stack);

        return stack;
    }
}
