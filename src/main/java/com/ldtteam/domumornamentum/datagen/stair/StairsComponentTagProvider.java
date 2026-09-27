package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class StairsComponentTagProvider implements BlockTagSection
{

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {
        host.tag(ModTags.STAIRS_MATERIALS)
          .add(
            Blocks.BLACKSTONE,
            Blocks.GILDED_BLACKSTONE,
            Blocks.NETHERRACK,
            Blocks.CRIMSON_NYLIUM,
            Blocks.WARPED_NYLIUM,
            Blocks.BASALT,
            Blocks.POLISHED_BASALT,
            Blocks.SMOOTH_BASALT,
            Blocks.HAY_BLOCK,
            Blocks.COPPER_BLOCK,
            Blocks.CUT_COPPER,
            Blocks.EXPOSED_COPPER,
            Blocks.OXIDIZED_COPPER,
            Blocks.WEATHERED_COPPER,
            Blocks.WAXED_COPPER_BLOCK,
            Blocks.WAXED_EXPOSED_COPPER,
            Blocks.WAXED_OXIDIZED_COPPER,
            Blocks.WAXED_WEATHERED_COPPER,
            Blocks.AMETHYST_BLOCK,
            Blocks.BUDDING_AMETHYST,
            Blocks.CUT_SANDSTONE,
            Blocks.CHISELED_SANDSTONE,
            Blocks.CHISELED_QUARTZ_BLOCK,
            Blocks.QUARTZ_PILLAR,
            Blocks.PACKED_ICE,
            Blocks.SNOW_BLOCK,
            Blocks.CHISELED_STONE_BRICKS,
            Blocks.CHISELED_RED_SANDSTONE,
            Blocks.CUT_RED_SANDSTONE,
            Blocks.CRACKED_STONE_BRICKS,
            Blocks.OBSIDIAN,
            Blocks.SMOOTH_STONE,
            Blocks.CALCITE,
            Blocks.BONE_BLOCK,
            Blocks.DRIED_KELP_BLOCK
          )
          .addTags(
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS,
            ModTags.CONCRETE,
            BlockTags.TERRACOTTA,
            BlockTags.DIRT,
            BlockTags.WOOL,
            BlockTags.LEAVES,
            Tags.Blocks.STORAGE_BLOCKS,
            Tags.Blocks.GLASS_BLOCKS,
            BlockTags.LOGS,
            BlockTags.CORAL_BLOCKS,
            BlockTags.WART_BLOCKS
          );
    }

}
