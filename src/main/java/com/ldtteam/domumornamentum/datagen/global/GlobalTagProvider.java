package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
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

        this.tag(ModTags.GLOBAL_DEFAULT).add(
          Blocks.MOSS_BLOCK,
          Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
          Blocks.CHISELED_POLISHED_BLACKSTONE,
          Blocks.POLISHED_BLACKSTONE,
          Blocks.BRICKS,
          Blocks.CALCITE,
          Blocks.HAY_BLOCK,
          Blocks.BLACKSTONE,
          Blocks.GILDED_BLACKSTONE,
          Blocks.POLISHED_BLACKSTONE_BRICKS,
          Blocks.NETHERRACK,
          Blocks.CRIMSON_NYLIUM,
          Blocks.WARPED_NYLIUM,
          Blocks.BASALT,
          Blocks.POLISHED_BASALT,
          Blocks.SMOOTH_BASALT,
          Blocks.DEEPSLATE_BRICKS,
          Blocks.POLISHED_DEEPSLATE,
          Blocks.PURPUR_BLOCK,
          Blocks.PURPUR_PILLAR,
          Blocks.END_STONE,
          Blocks.OBSIDIAN,
          Blocks.AMETHYST_BLOCK,
          Blocks.BUDDING_AMETHYST,
          Blocks.PACKED_ICE,
          Blocks.SNOW_BLOCK,
          Blocks.CRACKED_STONE_BRICKS,
          Blocks.SMOOTH_STONE,
          Blocks.CHISELED_STONE_BRICKS,
          Blocks.SANDSTONE,
          Blocks.CUT_SANDSTONE,
          Blocks.CHISELED_SANDSTONE,
          Blocks.RED_SANDSTONE,
          Blocks.CHISELED_RED_SANDSTONE,
          Blocks.CUT_RED_SANDSTONE,
          Blocks.SMOOTH_SANDSTONE,
          Blocks.SMOOTH_RED_SANDSTONE,
          Blocks.QUARTZ_PILLAR,
          Blocks.QUARTZ_BLOCK,
          Blocks.QUARTZ_BRICKS,
          Blocks.SMOOTH_QUARTZ,
          Blocks.CHISELED_QUARTZ_BLOCK,
          Blocks.RED_NETHER_BRICKS,
          Blocks.TUFF,
          Blocks.NETHER_BRICKS,
          Blocks.END_STONE_BRICKS,
          Blocks.PRISMARINE,
          Blocks.PRISMARINE_BRICKS,
          Blocks.DARK_PRISMARINE,
          Blocks.CHISELED_NETHER_BRICKS,
          Blocks.CHISELED_DEEPSLATE,
          Blocks.DEEPSLATE_BRICKS,
          Blocks.POLISHED_DEEPSLATE,
          Blocks.COBBLED_DEEPSLATE,
          Blocks.CRACKED_DEEPSLATE_BRICKS,
          Blocks.DEEPSLATE_TILES,
          Blocks.CRACKED_DEEPSLATE_TILES,
          Blocks.CALCITE,
          Blocks.TUFF,
          Blocks.BONE_BLOCK,
          Blocks.AZALEA_LEAVES,
          Blocks.FLOWERING_AZALEA_LEAVES,
          Blocks.MUD_BRICKS,
          Blocks.DRIED_KELP_BLOCK,
          Blocks.BAMBOO_BLOCK,
          Blocks.BAMBOO_MOSAIC,
          Blocks.BAMBOO_PLANKS,
          Blocks.STRIPPED_BAMBOO_BLOCK,
          Blocks.SCULK
        )
          .addTags(
            ModTags.EXTRA_BLOCKS,
            Tags.Blocks.END_STONES,
            ModTags.BRICKS,
            ModTags.CONCRETE,
            ModTags.COPPER,
            BlockTags.TERRACOTTA,
            BlockTags.WOOL,
            Tags.Blocks.STORAGE_BLOCKS,
            Tags.Blocks.GLASS_BLOCKS,
            BlockTags.LOGS,
            BlockTags.WART_BLOCKS,
            Tags.Blocks.STONES,
            Tags.Blocks.COBBLESTONES,
            Tags.Blocks.OBSIDIANS,
            BlockTags.STONE_BRICKS,
            BlockTags.BASE_STONE_NETHER
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
