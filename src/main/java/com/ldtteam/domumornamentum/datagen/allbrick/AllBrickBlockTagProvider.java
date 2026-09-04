package com.ldtteam.domumornamentum.datagen.allbrick;

import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addBlocks;

import com.ldtteam.domumornamentum.tag.ModTags;
import net.minecraft.data.tags.TagAppender;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class AllBrickBlockTagProvider extends BlockTagsProvider
{
    public AllBrickBlockTagProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        CompletableFuture<TagsProvider.TagLookup<Block>> parentProvider,
        @Nullable DatagenContext existingFileHelper
    ) {
        super(output, lookupProvider, parentProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        final var tagVar0 = this.tag(ModTags.ALL_BRICK_MATERIALS);
        addBlocks(tagVar0,
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
            Blocks.MUD_BRICKS,
            Blocks.DRIED_KELP_BLOCK,
            Blocks.POLISHED_ANDESITE,
            Blocks.POLISHED_DIORITE,
            Blocks.POLISHED_GRANITE
          );
        tagVar0.addTags(
            ModTags.EXTRA_BLOCKS,
            Tags.Blocks.END_STONES,
            ModTags.BRICKS,
            ModTags.CONCRETE,
            BlockTags.TERRACOTTA,
            ModTags.COPPER,
            BlockTags.WOOL,
            Tags.Blocks.STORAGE_BLOCKS,
            BlockTags.LOGS,
            BlockTags.WART_BLOCKS,
            Tags.Blocks.STONES,
            Tags.Blocks.COBBLESTONES,
            Tags.Blocks.OBSIDIANS,
            BlockTags.STONE_BRICKS,
            BlockTags.BASE_STONE_NETHER
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "All Brick Blocks Tag Provider";
    }
}
