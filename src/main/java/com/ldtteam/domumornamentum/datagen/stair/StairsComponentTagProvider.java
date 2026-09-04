package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.domumornamentum.tag.ModTags;
import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class StairsComponentTagProvider extends BlockTagsProvider
{
    public StairsComponentTagProvider(
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
        final var tagVar1 = this.tag(ModTags.STAIRS_MATERIALS);
        addBlocks(tagVar1,
            Blocks.BLACKSTONE,
            Blocks.GILDED_BLACKSTONE,
            Blocks.NETHERRACK,
            Blocks.CRIMSON_NYLIUM,
            Blocks.WARPED_NYLIUM,
            Blocks.BASALT,
            Blocks.POLISHED_BASALT,
            Blocks.SMOOTH_BASALT,
            Blocks.HAY_BLOCK,
            Blocks.COPPER_BLOCK.weathering().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.CUT_COPPER.weathering().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.COPPER_BLOCK.weathering().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.COPPER_BLOCK.weathering().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.COPPER_BLOCK.weathering().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.COPPER_BLOCK.waxed().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.COPPER_BLOCK.waxed().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.COPPER_BLOCK.waxed().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.COPPER_BLOCK.waxed().pick(WeatheringCopper.WeatherState.WEATHERED),
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
          );
        tagVar1.addTags(
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

    @Override
    @NotNull
    public String getName()
    {
        return "Stairs Tag Provider";
    }
}
