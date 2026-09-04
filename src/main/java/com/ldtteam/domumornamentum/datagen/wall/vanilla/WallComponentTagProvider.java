package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.ldtteam.domumornamentum.tag.ModTags;
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

public class WallComponentTagProvider extends BlockTagsProvider
{
    public WallComponentTagProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        CompletableFuture<TagsProvider.TagLookup<Block>> parentProvider,
        @Nullable DatagenContext existingFileHelper
    ) {
        super(output, lookupProvider, parentProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        final var wallMaterialsTag = this.tag(ModTags.WALL_MATERIALS);
        addBlocks(wallMaterialsTag,
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
          Blocks.POLISHED_ANDESITE,
          Blocks.POLISHED_DIORITE,
          Blocks.POLISHED_GRANITE,
          Blocks.SMOOTH_SANDSTONE,
          Blocks.CUT_SANDSTONE,
          Blocks.QUARTZ_BLOCK,
          Blocks.QUARTZ_BRICKS,
          Blocks.QUARTZ_PILLAR,
          Blocks.CHISELED_QUARTZ_BLOCK,
          Blocks.CALCITE,
          Blocks.BONE_BLOCK,
          Blocks.AZALEA_LEAVES,
          Blocks.FLOWERING_AZALEA_LEAVES,
          Blocks.CHISELED_RED_SANDSTONE,
          Blocks.CUT_RED_SANDSTONE
        );
        wallMaterialsTag.addTags(
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS,
            ModTags.CONCRETE,
            BlockTags.TERRACOTTA,
            ModTags.COPPER,
            BlockTags.WOOL,
            Tags.Blocks.STORAGE_BLOCKS,
            Tags.Blocks.GLASS_BLOCKS,
            BlockTags.LOGS,
            BlockTags.WART_BLOCKS,
            BlockTags.PLANKS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Wall Tag Provider";
    }

    private void addBlocks(final net.minecraft.data.tags.TagAppender<net.minecraft.world.level.block.Block> appender, final net.minecraft.world.level.block.Block... blocks)
    {
        for (final var block : blocks)
        {
            appender.add(net.minecraft.resources.ResourceKey.create(
                net.minecraft.core.registries.Registries.BLOCK,
                net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block)));
        }
    }
}
