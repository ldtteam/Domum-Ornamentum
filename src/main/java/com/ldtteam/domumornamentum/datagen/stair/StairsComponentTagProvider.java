package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class StairsComponentTagProvider extends BlockTagsProvider
{
    public StairsComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.STAIRS_MATERIALS)
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
            Blocks.CRACKED_STONE_BRICKS,
            Blocks.OBSIDIAN,
            Blocks.SMOOTH_STONE,
            Blocks.CALCITE,
            Blocks.BONE_BLOCK,
            Blocks.DRIED_KELP_BLOCK,
            Blocks.DIRT_PATH
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
            Tags.Blocks.GLASS,
            BlockTags.LOGS,
            BlockTags.CORAL_BLOCKS,
            BlockTags.WART_BLOCKS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Stairs Tag Provider";
    }
}
