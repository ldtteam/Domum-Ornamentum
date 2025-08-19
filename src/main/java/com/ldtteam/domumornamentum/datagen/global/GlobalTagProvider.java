package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GlobalTagProvider extends BlockTagsProvider
{
    public GlobalTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.CONCRETE)
          .add(
            Blocks.BLACK_CONCRETE,
            Blocks.CYAN_CONCRETE,
            Blocks.BLUE_CONCRETE,
            Blocks.BROWN_CONCRETE,
            Blocks.GRAY_CONCRETE,
            Blocks.GREEN_CONCRETE,
            Blocks.LIGHT_BLUE_CONCRETE,
            Blocks.LIGHT_GRAY_CONCRETE,
            Blocks.LIME_CONCRETE,
            Blocks.MAGENTA_CONCRETE,
            Blocks.ORANGE_CONCRETE,
            Blocks.PINK_CONCRETE,
            Blocks.PURPLE_CONCRETE,
            Blocks.RED_CONCRETE,
            Blocks.WHITE_CONCRETE,
            Blocks.YELLOW_CONCRETE);

        this.tag(ModTags.GLACED_TERRACOTTA)
          .add(
            Blocks.WHITE_GLAZED_TERRACOTTA,
            Blocks.ORANGE_GLAZED_TERRACOTTA,
            Blocks.MAGENTA_GLAZED_TERRACOTTA,
            Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,
            Blocks.YELLOW_GLAZED_TERRACOTTA,
            Blocks.LIME_GLAZED_TERRACOTTA,
            Blocks.PINK_GLAZED_TERRACOTTA,
            Blocks.GRAY_GLAZED_TERRACOTTA,
            Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
            Blocks.CYAN_GLAZED_TERRACOTTA,
            Blocks.PURPLE_GLAZED_TERRACOTTA,
            Blocks.BLUE_GLAZED_TERRACOTTA,
            Blocks.BROWN_GLAZED_TERRACOTTA,
            Blocks.GREEN_GLAZED_TERRACOTTA,
            Blocks.RED_GLAZED_TERRACOTTA,
            Blocks.BLACK_GLAZED_TERRACOTTA);

        this.tag(ModTags.COPPER).add(
          Blocks.COPPER_BLOCK,
          Blocks.WAXED_COPPER_BLOCK,
          Blocks.EXPOSED_COPPER,
          Blocks.WAXED_EXPOSED_COPPER,
          Blocks.WEATHERED_COPPER,
          Blocks.WAXED_WEATHERED_COPPER,
          Blocks.OXIDIZED_COPPER,
          Blocks.WAXED_OXIDIZED_COPPER,
          Blocks.CUT_COPPER,
          Blocks.WAXED_CUT_COPPER,
          Blocks.EXPOSED_CUT_COPPER,
          Blocks.WAXED_EXPOSED_CUT_COPPER,
          Blocks.WEATHERED_CUT_COPPER,
          Blocks.WAXED_WEATHERED_CUT_COPPER,
          Blocks.OXIDIZED_CUT_COPPER,
          Blocks.WAXED_OXIDIZED_CUT_COPPER);

        this.tag(ModTags.GLOBAL_DEFAULT_EXCLUDE_VANILLA_VARIANTS).add(
                        // Overworld base
                        Blocks.BUDDING_AMETHYST,
                        Blocks.SCULK,
                        Blocks.SCULK_CATALYST,
                        Blocks.CRYING_OBSIDIAN,
                        Blocks.MAGMA_BLOCK,
                        Blocks.DRIED_KELP_BLOCK,
                        Blocks.BROWN_MUSHROOM_BLOCK,
                        Blocks.RED_MUSHROOM_BLOCK,
                        Blocks.DRIPSTONE_BLOCK,
                        Blocks.LODESTONE,
                        Blocks.RESPAWN_ANCHOR,
                        Blocks.TARGET,
                        Blocks.HONEY_BLOCK,
                        Blocks.HONEYCOMB_BLOCK,
                        Blocks.SLIME_BLOCK,
                        Blocks.SPONGE,
                        Blocks.WET_SPONGE,
                        // Mud
                        Blocks.PACKED_MUD,
                        // Stones
                        Blocks.CALCITE,
                        Blocks.CRACKED_STONE_BRICKS,
                        // Deepslate
                        Blocks.CHISELED_DEEPSLATE,
                        Blocks.CRACKED_DEEPSLATE_BRICKS,
                        Blocks.CRACKED_DEEPSLATE_TILES,
                        // Coral
                        Blocks.DEAD_BRAIN_CORAL_BLOCK,
                        Blocks.DEAD_BUBBLE_CORAL_BLOCK,
                        Blocks.DEAD_FIRE_CORAL_BLOCK,
                        Blocks.DEAD_TUBE_CORAL_BLOCK,
                        Blocks.DEAD_HORN_CORAL_BLOCK,

                        // Nether base
                        Blocks.GILDED_BLACKSTONE,
                        Blocks.BONE_BLOCK,
                        Blocks.SOUL_SAND,
                        Blocks.SOUL_SOIL,
                        // Nether bricks
                        Blocks.CRACKED_NETHER_BRICKS,
                        Blocks.CHISELED_NETHER_BRICKS,
                        Blocks.RED_NETHER_BRICKS,
                        // Blackstone
                        Blocks.CHISELED_POLISHED_BLACKSTONE,
                        Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
                        // Basalt
                        Blocks.POLISHED_BASALT,
                        Blocks.SMOOTH_BASALT,
                        // Quartz
                        Blocks.QUARTZ_PILLAR,
                        Blocks.QUARTZ_BRICKS,
                        Blocks.CHISELED_QUARTZ_BLOCK,

                        // Purpur
                        Blocks.PURPUR_PILLAR
                )
                .addTags(
                        BlockTags.DIRT,
                        BlockTags.LEAVES,
                        BlockTags.LOGS,
                        BlockTags.BAMBOO_BLOCKS,
                        BlockTags.WOOL,
                        BlockTags.WART_BLOCKS,
                        BlockTags.NYLIUM,
                        BlockTags.TERRACOTTA,
                        BlockTags.SNOW,
                        BlockTags.ICE,
                        BlockTags.CORAL_BLOCKS,
                        Tags.Blocks.END_STONES,
                        Tags.Blocks.SAND,
                        Tags.Blocks.GRAVEL,
                        Tags.Blocks.OBSIDIAN,
                        Tags.Blocks.NETHERRACK,
                        Tags.Blocks.ORES,
                        Tags.Blocks.STORAGE_BLOCKS,
                        Tags.Blocks.GLASS,
                        Tags.Blocks.BOOKSHELVES,
                        ModTags.EXTRA_BLOCKS,
                        ModTags.BRICKS,
                        ModTags.CONCRETE,
                        ModTags.COPPER,
                        ModTags.GLACED_TERRACOTTA
                );

        this.tag(ModTags.GLOBAL_DEFAULT).add(
                        // Overworld base
                        Blocks.BRICKS,
                        Blocks.MUD_BRICKS,
                        Blocks.BAMBOO_MOSAIC,
                        // Stones
                        Blocks.SMOOTH_STONE,
                        Blocks.POLISHED_ANDESITE,
                        Blocks.POLISHED_DIORITE,
                        Blocks.POLISHED_GRANITE,
                        // Deepslate
                        Blocks.POLISHED_DEEPSLATE,
                        Blocks.DEEPSLATE_BRICKS,
                        Blocks.DEEPSLATE_TILES,
                        // Primsarine
                        Blocks.PRISMARINE,
                        Blocks.PRISMARINE_BRICKS,
                        Blocks.DARK_PRISMARINE,

                        // Nether brick
                        Blocks.NETHER_BRICKS,
                        // Blackstone
                        Blocks.POLISHED_BLACKSTONE,
                        Blocks.POLISHED_BLACKSTONE_BRICKS,
                        // Quartz
                        Blocks.QUARTZ_BLOCK,
                        Blocks.SMOOTH_QUARTZ,

                        // End base
                        Blocks.END_STONE_BRICKS,
                        // Purpur
                        Blocks.PURPUR_BLOCK
                )
                .addTags(
                        ModTags.GLOBAL_DEFAULT_EXCLUDE_VANILLA_VARIANTS,
                        BlockTags.PLANKS,
                        BlockTags.BASE_STONE_OVERWORLD,
                        BlockTags.BASE_STONE_NETHER,
                        BlockTags.STONE_BRICKS,
                        Tags.Blocks.COBBLESTONE_NORMAL,
                        Tags.Blocks.COBBLESTONE_DEEPSLATE,
                        Tags.Blocks.COBBLESTONE_MOSSY,
                        Tags.Blocks.STONE,
                        Tags.Blocks.SANDSTONE
                );

        this.tag(BlockTags.MINEABLE_WITH_AXE)
          .add(ModBlocks.getInstance().getArchitectsCutter(),
            ModBlocks.getInstance().getLayingBarrel(),
            ModBlocks.getInstance().getStandingBarrel());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
          .add(ModBlocks.getInstance().getBricks().toArray(new Block[0]));

        ModBlocks.getInstance().getExtraTopBlocks().forEach(extraBlock -> this.tag(extraBlock.getType().getCategory().getMineableTag()).add(extraBlock));

        this.tag(BlockTags.DOORS)
          .add(ModBlocks.getInstance().getDoor())
          .add(ModBlocks.getInstance().getFancyDoor());

        this.tag(BlockTags.WOODEN_DOORS)
          .add(ModBlocks.getInstance().getDoor())
          .add(ModBlocks.getInstance().getFancyDoor());

        this.tag(BlockTags.STAIRS)
          .add(ModBlocks.getInstance().getStair())
          .add(ModBlocks.getInstance().getAllBrickStairBlocks().toArray(new Block[0]));
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Global Tag Provider";
    }
}
