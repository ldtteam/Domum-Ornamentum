package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.DomumOrnamentum;
import com.ldtteam.domumornamentum.block.decorative.AllBrickBlock;
import com.ldtteam.domumornamentum.block.decorative.AllBrickStairBlock;
import com.ldtteam.domumornamentum.block.decorative.FancyDoorBlock;
import com.ldtteam.domumornamentum.block.decorative.FancyTrapdoorBlock;
import com.ldtteam.domumornamentum.block.decorative.FramedLightBlock;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
import com.ldtteam.domumornamentum.block.types.FramedLightType;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.block.vanilla.DoorBlock;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Stable, human-oriented ordering for the Architect's Cutter.
 *
 * <p>The registry order is not a UI contract and changed during the 26.2
 * registration migration.  Keeping this ordering explicit preserves the
 * category and variant layout from the 1.21 client.</p>
 */
public final class SortedBlocks
{
    private static final Map<Block, Function<ItemStack, Double>> SORTING_INDEX = new HashMap<>();
    private static final Map<Identifier, Integer> GROUP_SORTING_INDEX = new HashMap<>();

    private SortedBlocks()
    {
    }

    private static void init()
    {
        final ModBlocks blocks = ModBlocks.getInstance();

        // Vanilla-compatible shapes.
        group(blocks, "avanilla", 1);
        simple(blocks.getStair(), 11);
        simple(blocks.getSlab(), 12);
        simple(blocks.getFence(), 13);
        simple(blocks.getFenceGate(), 14);
        simple(blocks.getWall(), 15);

        // Timber frames.
        group(blocks, "btimberframe", 2);
        for (final TimberFrameBlock block : blocks.getTimberFrames())
        {
            simple(block, switch (block.getTimberFrameType())
            {
                case FRAMED -> 1;
                case DOUBLE_CROSSED -> 2;
                case PLAIN -> 3;
                case SIDE_FRAMED -> 4;
                case UP_GATED -> 5;
                case DOWN_GATED -> 6;
                case ONE_CROSSED_LR -> 7;
                case ONE_CROSSED_RL -> 8;
                case HORIZONTAL_PLAIN -> 9;
                case SIDE_FRAMED_HORIZONTAL -> 10;
            });
        }
        simple(blocks.getDynamicTimberFrame(), 3.1);

        // Shingles.  26.2 keeps the three supported heights from the port.
        group(blocks, "cshingle", 3);
        simple(blocks.getShingle(ShingleHeightType.DEFAULT), 16);
        simple(blocks.getShingle(ShingleHeightType.FLAT), 17);
        simple(blocks.getShingle(ShingleHeightType.FLAT_LOWER), 18);
        simple(blocks.getShingleSlab(), 21);

        // Trapdoors and doors use the block-state component on their item.
        group(blocks, "etrapdoor", 4);
        state(blocks.getTrapdoor(), s -> switch (BlockUtils.getPropertyFromBlockStateTag(s, TrapdoorBlock.TYPE, TrapdoorType.WAFFLE))
        {
            case WAFFLE -> 27.0;
            case HORIZONTALLY_SQUIGGLY_STRIPED -> 28.0;
            case VERTICALLY_STRIPED -> 30.0;
            case HORIZONTALLY_STRIPED -> 31.0;
            case PORT_MANTEAU -> 32.0;
            case VERTICAL_BARS -> 33.0;
            case HORIZONTAL_BARS -> 34.0;
            case VERTICALLY_SQUIGGLY_STRIPED -> 35.0;
            case FULL -> 36.0;
            case SLOT -> 38.0;
            case PORTHOLE -> 39.0;
            case MOULDING -> 40.0;
            case COFFER -> 41.0;
            case BOSS -> 42.0;
            case ROUNDEL -> 43.0;
        });
        state(blocks.getFancyTrapdoor(), s -> switch (BlockUtils.getPropertyFromBlockStateTag(s, FancyTrapdoorBlock.TYPE, FancyTrapdoorType.CREEPER))
        {
            case FULL -> 29.0;
            case CREEPER -> 37.0;
        });

        group(blocks, "ddoor", 5);
        state(blocks.getFancyDoor(), s -> switch (BlockUtils.getPropertyFromBlockStateTag(s, FancyDoorBlock.TYPE, FancyDoorType.CREEPER))
        {
            case FULL -> 22.0;
            case CREEPER -> 27.0;
        });
        state(blocks.getDoor(), s -> switch (BlockUtils.getPropertyFromBlockStateTag(s, DoorBlock.TYPE, DoorType.FULL))
        {
            case VERTICALLY_STRIPED -> 23.0;
            case WAFFLE -> 24.0;
            case PORT_MANTEAU -> 25.0;
            case FULL -> 26.0;
        });

        // Panels.
        group(blocks, "fpanel", 6);
        state(blocks.getPanel(), s -> switch (BlockUtils.getPropertyFromBlockStateTag(s, TrapdoorBlock.TYPE, TrapdoorType.WAFFLE))
        {
            case FULL -> 44.0;
            case WAFFLE -> 45.0;
            case VERTICALLY_STRIPED -> 46.0;
            case HORIZONTALLY_STRIPED -> 47.0;
            case PORT_MANTEAU -> 48.0;
            case MOULDING -> 49.0;
            case COFFER -> 50.0;
            case VERTICAL_BARS -> 51.0;
            case HORIZONTAL_BARS -> 52.0;
            case VERTICALLY_SQUIGGLY_STRIPED -> 53.0;
            case HORIZONTALLY_SQUIGGLY_STRIPED -> 54.0;
            case SLOT -> 55.0;
            case PORTHOLE -> 56.0;
            case ROUNDEL -> 57.0;
            case BOSS -> 58.0;
        });

        // Paper walls, lights, pillars, bricks and posts.
        group(blocks, "hpaperwall", 7);
        group(blocks, "ilight", 8);
        for (final FramedLightBlock block : blocks.getFramedLights())
        {
            simple(block, switch (block.getFramedLightType())
            {
                case CENTER -> 62;
                case LANTERN -> 63;
                case FRAMED -> 64;
                case VERTICAL -> 65;
                case CROSSED -> 66;
                case HORIZONTAL -> 67;
                case FOUR -> 68;
            });
        }

        group(blocks, "gpillar", 9);
        final List<? extends Block> pillars = blocks.getPillars();
        for (final Block block : pillars)
        {
            final String path = idPath(block);
            simple(block, path.equals("squarepillar") ? 59 : path.equals("blockypillar") ? 60 : 61);
        }

        group(blocks, "kpost", 10);
        state(blocks.getPost(), s -> switch (BlockUtils.getPropertyFromBlockStateTag(s, PostBlock.TYPE, PostType.PLAIN))
        {
            case PLAIN -> 73.0;
            case DOUBLE -> 74.0;
            case QUAD -> 75.0;
            case HEAVY -> 76.0;
            case TURNED -> 77.0;
            case PINCHED -> 78.0;
        });

        group(blocks, "jbrick", 11);
        final List<? extends Block> bricks = blocks.getAllBrickBlocks();
        if (bricks.size() > 0) simple(bricks.get(0), 69);
        if (bricks.size() > 1) simple(bricks.get(1), 71);
        final List<? extends Block> brickStairs = blocks.getAllBrickStairBlocks();
        if (brickStairs.size() > 0) simple(brickStairs.get(0), 70);
        if (brickStairs.size() > 1) simple(brickStairs.get(1), 72);
    }

    private static void group(final ModBlocks ignored, final String path, final int index)
    {
        GROUP_SORTING_INDEX.put(Constants.resLocDO(path), index);
    }

    private static void simple(final Block block, final double index)
    {
        SORTING_INDEX.put(block, ignored -> index);
    }

    private static void state(final Block block, final Function<ItemStack, Double> index)
    {
        SORTING_INDEX.put(block, index);
    }

    private static String idPath(final Block block)
    {
        final Identifier id = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block);
        return id == null ? "" : id.getPath();
    }

    private static double getSortIndex(final ItemStack stack)
    {
        try
        {
            if (SORTING_INDEX.isEmpty())
            {
                init();
            }
            if (stack.getItem() instanceof BlockItem blockItem)
            {
                return SORTING_INDEX.getOrDefault(blockItem.getBlock(), ignored -> Double.MAX_VALUE).apply(stack);
            }
        }
        catch (final Exception exception)
        {
            DomumOrnamentum.LOGGER.info("Failed to sort Architect's Cutter item: " + stack, exception);
        }
        return Double.MAX_VALUE;
    }

    public static List<ItemStack> sortItems(final List<ItemStack> stackList)
    {
        stackList.sort(Comparator.comparingDouble(SortedBlocks::getSortIndex));
        return stackList;
    }

    public static void sortGroups(final List<Identifier> ids)
    {
        if (GROUP_SORTING_INDEX.isEmpty())
        {
            init();
        }
        ids.sort(Comparator.comparingInt(id -> GROUP_SORTING_INDEX.getOrDefault(id, Integer.MAX_VALUE)));
    }
}
