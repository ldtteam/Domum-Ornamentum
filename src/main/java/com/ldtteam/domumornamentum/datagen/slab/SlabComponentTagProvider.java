package com.ldtteam.domumornamentum.datagen.slab;

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

public class SlabComponentTagProvider extends BlockTagsProvider {
    public SlabComponentTagProvider(
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

        final var slabMaterialsTag = this.tag(ModTags.SLAB_MATERIALS);
        addBlocks(slabMaterialsTag,

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
                        Blocks.BOOKSHELF,
                        Blocks.AMETHYST_BLOCK,
                        Blocks.BUDDING_AMETHYST,
                        Blocks.CHISELED_SANDSTONE,
                        Blocks.CHISELED_QUARTZ_BLOCK,
                        Blocks.QUARTZ_PILLAR,
                        Blocks.PACKED_ICE,
                        Blocks.SNOW_BLOCK,
                        Blocks.CHISELED_STONE_BRICKS,
                        Blocks.CRACKED_STONE_BRICKS,
                        Blocks.OBSIDIAN,
                        Blocks.CALCITE,
                        Blocks.BONE_BLOCK,
                        Blocks.DRIED_KELP_BLOCK,
                        Blocks.DIRT_PATH
        );
        slabMaterialsTag.addTags(
                        ModTags.GLOBAL_DEFAULT,
                        BlockTags.PLANKS,
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
    public String getName() {
        return "Slab Tag Provider";
    }
}
