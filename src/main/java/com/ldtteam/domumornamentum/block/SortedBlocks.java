package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.DomumOrnamentum;
import com.ldtteam.domumornamentum.block.decorative.*;
import com.ldtteam.domumornamentum.block.types.*;
import com.ldtteam.domumornamentum.block.vanilla.DoorBlock;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility for visually sorting architects cutter groups
 * Sort is arbitrary by most commonly used types
 */
public class SortedBlocks
{
    /**
     * Sorting indexes for blocks, by block type through itemstack predicates to allow block type/state specifics
     */
    private static Map<Block, Function<ItemStack, Double>> sortingIndex = new HashMap<>();

    /**
     * Group indexes by ID
     */
    private static Map<ResourceLocation, Integer> groupSortingIndex = new HashMap<>();

    /**
     * Initializes sorting indexes
     */
    private static void init()
    {
        final ModBlocks modBlocks = ModBlocks.getInstance();

        // Timberframes
        groupSortingIndex.put(Constants.resLocDO("btimberframe"), 2);
        for (final TimberFrameBlock block : modBlocks.getTimberFrames())
        {
            switch (block.getTimberFrameType())
            {
                case FRAMED -> simpleBlockIndex(block, 1);
                case DOUBLE_CROSSED -> simpleBlockIndex(block, 2);
                case PLAIN -> simpleBlockIndex(block, 3);
                case SIDE_FRAMED -> simpleBlockIndex(block, 4);
                case UP_GATED -> simpleBlockIndex(block, 5);
                case DOWN_GATED -> simpleBlockIndex(block, 6);
                case ONE_CROSSED_LR -> simpleBlockIndex(block, 7);
                case ONE_CROSSED_RL -> simpleBlockIndex(block, 8);
                case HORIZONTAL_PLAIN -> simpleBlockIndex(block, 9);
                case SIDE_FRAMED_HORIZONTAL -> simpleBlockIndex(block, 10);
            }
        }
        simpleBlockIndex(modBlocks.getDynamicTimberFrame(), 3.1);

        // Vanilla blocks
        groupSortingIndex.put(Constants.resLocDO("avanilla"), 1);
        simpleBlockIndex(modBlocks.getStair(), 11);
        simpleBlockIndex(modBlocks.getSlab(), 12);
        simpleBlockIndex(modBlocks.getFence(), 13);
        simpleBlockIndex(modBlocks.getFenceGate(), 14);
        simpleBlockIndex(modBlocks.getWall(), 15);

        // Shingles
        groupSortingIndex.put(Constants.resLocDO("cshingle"), 3);
        simpleBlockIndex(modBlocks.getShingle(ShingleHeightType.DEFAULT), 16);
        simpleBlockIndex(modBlocks.getShingle(ShingleHeightType.FLAT), 17);
        simpleBlockIndex(modBlocks.getShingle(ShingleHeightType.FLAT_LOWER), 18);
        simpleBlockIndex(modBlocks.getShingle(ShingleHeightType.STEEP), 19);
        simpleBlockIndex(modBlocks.getShingle(ShingleHeightType.STEEP_LOWER), 20);
        simpleBlockIndex(modBlocks.getShingleSlab(), 21);

        // Doors
        groupSortingIndex.put(Constants.resLocDO("ddoor"), 5);
        sortingIndex.put(modBlocks.getFancyDoor(), s -> {
            double index = Double.MAX_VALUE;
            switch (BlockUtils.getPropertyFromBlockStateTag(s, FancyDoorBlock.TYPE, FancyDoorType.CREEPER))
            {
                case FULL -> index = 22;

                case CREEPER -> index = 27;
            }
            return index;
        });
        sortingIndex.put(modBlocks.getDoor(), s -> {
            double index = Double.MAX_VALUE;
            switch (BlockUtils.getPropertyFromBlockStateTag(s, DoorBlock.TYPE, DoorType.FULL))
            {
                case VERTICALLY_STRIPED -> index = 23;
                case WAFFLE -> index = 24;
                case PORT_MANTEAU -> index = 25;
                case FULL -> index = 26;
            }
            return index;
        });

        // Trapdoors
        groupSortingIndex.put(Constants.resLocDO("etrapdoor"), 4);
        sortingIndex.put(modBlocks.getTrapdoor(), s -> {
            double index = Double.MAX_VALUE;
            switch (BlockUtils.getPropertyFromBlockStateTag(s, TrapdoorBlock.TYPE, TrapdoorType.WAFFLE))
            {
                case WAFFLE -> index = 27;
                case HORIZONTALLY_SQUIGGLY_STRIPED -> index = 28;

                case VERTICALLY_STRIPED -> index = 30;
                case HORIZONTALLY_STRIPED -> index = 31;
                case PORT_MANTEAU -> index = 32;
                case VERTICAL_BARS -> index = 33;
                case HORIZONTAL_BARS -> index = 34;
                case VERTICALLY_SQUIGGLY_STRIPED -> index = 35;
                case FULL -> index = 36;

                case SLOT -> index = 38;
                case PORTHOLE -> index = 39;
                case MOULDING -> index = 40;
                case COFFER -> index = 41;
                case BOSS -> index = 42;
                case ROUNDEL -> index = 43;
            }
            return index;
        });
        sortingIndex.put(modBlocks.getFancyTrapdoor(), s -> {
            double index = Double.MAX_VALUE;
            switch (BlockUtils.getPropertyFromBlockStateTag(s, FancyTrapdoorBlock.TYPE, FancyTrapdoorType.CREEPER))
            {
                case FULL -> index = 29;
                case CREEPER -> index = 37;
            }
            return index;
        });

        // Panels
        groupSortingIndex.put(Constants.resLocDO("fpanel"), 6);
        sortingIndex.put(modBlocks.getPanel(), s -> {
            double index = Double.MAX_VALUE;
            switch (BlockUtils.getPropertyFromBlockStateTag(s, TrapdoorBlock.TYPE, TrapdoorType.WAFFLE))
            {
                case FULL -> index = 44;
                case WAFFLE -> index = 45;
                case VERTICALLY_STRIPED -> index = 46;
                case HORIZONTALLY_STRIPED -> index = 47;
                case PORT_MANTEAU -> index = 48;
                case MOULDING -> index = 49;
                case COFFER -> index = 50;
                case VERTICAL_BARS -> index = 51;
                case HORIZONTAL_BARS -> index = 52;
                case VERTICALLY_SQUIGGLY_STRIPED -> index = 53;
                case HORIZONTALLY_SQUIGGLY_STRIPED -> index = 54;
                case SLOT -> index = 55;
                case PORTHOLE -> index = 56;
                case ROUNDEL -> index = 57;
                case BOSS -> index = 58;
            }
            return index;
        });

        // Pillar
        groupSortingIndex.put(Constants.resLocDO("gpillar"), 9);
        simpleBlockIndex(modBlocks.getSqaurePillar(), 59);
        simpleBlockIndex(modBlocks.getVoxelPillar(), 60);
        simpleBlockIndex(modBlocks.getRoundPillar(), 61);

        // Framed Panes(paper walls)
        groupSortingIndex.put(Constants.resLocDO("hpaperwall"), 7);

        // Lights
        groupSortingIndex.put(Constants.resLocDO("ilight"), 8);
        for (final FramedLightBlock framedLightBlock : modBlocks.getFramedLights())
        {
            double index = Double.MAX_VALUE;
            switch (framedLightBlock.getFramedLightType())
            {
                case CENTER -> simpleBlockIndex(framedLightBlock, 62);
                case LANTERN -> simpleBlockIndex(framedLightBlock, 63);
                case FRAMED -> simpleBlockIndex(framedLightBlock, 64);
                case VERTICAL -> simpleBlockIndex(framedLightBlock, 65);
                case CROSSED -> simpleBlockIndex(framedLightBlock, 66);
                case HORIZONTAL -> simpleBlockIndex(framedLightBlock, 67);
                case FOUR -> simpleBlockIndex(framedLightBlock, 68);
            }
        }

        // Bricks
        groupSortingIndex.put(Constants.resLocDO("jbrick"), 11);
        simpleBlockIndex(modBlocks.getAllBrickBlock(), 69);
        simpleBlockIndex(modBlocks.getAllBrickStairBlock(), 70);
        simpleBlockIndex(modBlocks.getAllBrickDarkBlock(), 71);
        simpleBlockIndex(modBlocks.getAllBrickDarkStairBlock(), 72);
        // Posts
        groupSortingIndex.put(Constants.resLocDO("kpost"), 10);
        sortingIndex.put(modBlocks.getPost(), s -> {
            double index = Double.MAX_VALUE;
            switch (BlockUtils.getPropertyFromBlockStateTag(s, PostBlock.TYPE, PostType.PLAIN))
            {
                case PLAIN -> index = 73;
                case DOUBLE -> index = 74;
                case QUAD -> index = 75;
                case HEAVY -> index = 76;
                case TURNED -> index = 77;
                case PINCHED -> index = 78;
            }
            return index;
        });
    }

    private static void simpleBlockIndex(Block block, double index)
    {
        sortingIndex.put(block, s -> index);
    }

    private static double getSortIndex(final ItemStack stack)
    {
        try
        {
            if (sortingIndex.isEmpty())
            {
                init();
            }

            if (stack.getItem() instanceof BlockItem blockItem)
            {
                return sortingIndex.getOrDefault(blockItem.getBlock(), s -> Double.MAX_VALUE).apply(stack);
            }
        }
        catch (Exception e)
        {
            DomumOrnamentum.LOGGER.info("Failed to sort category for:" + stack, e);
        }
        return Double.MAX_VALUE;
    }

    /**
     * Sorts the given list of stacks by their block sorting
     *
     * @param stackList
     * @return
     */
    public static List<ItemStack> sortItems(final List<ItemStack> stackList)
    {
        stackList.sort(Comparator.comparingDouble(SortedBlocks::getSortIndex));
        return stackList;
    }

    /***
     * Sorts the group resourcelocation IDs
     * @param ids
     */
    public static void sortGroups(final List<ResourceLocation> ids)
    {
        ids.sort(Comparator.comparingInt(id -> groupSortingIndex.get(id)));
    }
}
