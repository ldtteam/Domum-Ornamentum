package com.ldtteam.domumornamentum.datagen.global;

import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addBlocks;
import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addKeys;

import com.ldtteam.domumornamentum.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.neoforged.neoforge.common.Tags;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GlobalTagProvider extends BlockTagsProvider
{
    public GlobalTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable DatagenContext existingFileHelper) {
        this(output, lookupProvider, CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()), existingFileHelper);
    }

    public GlobalTagProvider(
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
        addBlocks(this.tag(ModTags.CONCRETE),
            Blocks.CONCRETE.pick(DyeColor.BLACK),
            Blocks.CONCRETE.pick(DyeColor.CYAN),
            Blocks.CONCRETE.pick(DyeColor.BLUE),
            Blocks.CONCRETE.pick(DyeColor.BROWN),
            Blocks.CONCRETE.pick(DyeColor.GRAY),
            Blocks.CONCRETE.pick(DyeColor.GREEN),
            Blocks.CONCRETE.pick(DyeColor.LIGHT_BLUE),
            Blocks.CONCRETE.pick(DyeColor.LIGHT_GRAY),
            Blocks.CONCRETE.pick(DyeColor.LIME),
            Blocks.CONCRETE.pick(DyeColor.MAGENTA),
            Blocks.CONCRETE.pick(DyeColor.ORANGE),
            Blocks.CONCRETE.pick(DyeColor.PINK),
            Blocks.CONCRETE.pick(DyeColor.PURPLE),
            Blocks.CONCRETE.pick(DyeColor.RED),
            Blocks.CONCRETE.pick(DyeColor.WHITE),
            Blocks.CONCRETE.pick(DyeColor.YELLOW));

        final var tagVar0 = this.tag(ModTags.GLACED_TERRACOTTA);
        addBlocks(tagVar0,
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.WHITE),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.ORANGE),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.MAGENTA),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.LIGHT_BLUE),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.YELLOW),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.LIME),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.PINK),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.GRAY),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.LIGHT_GRAY),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.CYAN),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.PURPLE),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.BLUE),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.BROWN),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.GREEN),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.RED),
            Blocks.GLAZED_TERRACOTTA.pick(DyeColor.BLACK));

        final var tagVar1 = this.tag(ModTags.COPPER);
        addBlocks(tagVar1,
            Blocks.COPPER_BLOCK.weathering().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.COPPER_BLOCK.waxed().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.COPPER_BLOCK.weathering().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.COPPER_BLOCK.waxed().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.COPPER_BLOCK.weathering().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.COPPER_BLOCK.waxed().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.COPPER_BLOCK.weathering().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.COPPER_BLOCK.waxed().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.CUT_COPPER.weathering().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.CUT_COPPER.waxed().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.CUT_COPPER.weathering().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.CUT_COPPER.waxed().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.CUT_COPPER.weathering().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.CUT_COPPER.waxed().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.CUT_COPPER.weathering().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.CUT_COPPER.waxed().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.CHISELED_COPPER.weathering().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.CHISELED_COPPER.waxed().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.CHISELED_COPPER.weathering().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.CHISELED_COPPER.waxed().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.CHISELED_COPPER.weathering().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.CHISELED_COPPER.waxed().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.CHISELED_COPPER.weathering().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.CHISELED_COPPER.waxed().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.COPPER_GRATE.weathering().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.COPPER_GRATE.waxed().pick(WeatheringCopper.WeatherState.UNAFFECTED),
            Blocks.COPPER_GRATE.weathering().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.COPPER_GRATE.waxed().pick(WeatheringCopper.WeatherState.EXPOSED),
            Blocks.COPPER_GRATE.weathering().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.COPPER_GRATE.waxed().pick(WeatheringCopper.WeatherState.WEATHERED),
            Blocks.COPPER_GRATE.weathering().pick(WeatheringCopper.WeatherState.OXIDIZED),
            Blocks.COPPER_GRATE.waxed().pick(WeatheringCopper.WeatherState.OXIDIZED));

        final var tagVar2 = this.tag(ModTags.GLOBAL_DEFAULT);
        addBlocks(tagVar2,
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
            Blocks.SCULK,
            Blocks.PACKED_MUD,
            Blocks.BROWN_MUSHROOM_BLOCK,
            Blocks.RED_MUSHROOM_BLOCK,
            Blocks.MAGMA_BLOCK,
            Blocks.CRYING_OBSIDIAN,
            Blocks.OBSIDIAN,
            Blocks.POLISHED_ANDESITE,
            Blocks.POLISHED_DIORITE,
            Blocks.POLISHED_GRANITE,
            Blocks.TUFF_BRICKS,
            Blocks.CHISELED_TUFF,
            Blocks.CHISELED_TUFF_BRICKS,
            Blocks.POLISHED_TUFF
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

        addKeys(this.tag(BlockTags.MINEABLE_WITH_AXE), List.of(
            keyFor(ModBlocks.getInstance().getArchitectsCutter()),
            keyFor(ModBlocks.getInstance().getLayingBarrel()),
            keyFor(ModBlocks.getInstance().getStandingBarrel())
        ));

        addKeys(this.tag(BlockTags.MINEABLE_WITH_PICKAXE),
            ModBlocks.getInstance().getBricks().stream()
                .map(GlobalTagProvider::keyFor).collect(Collectors.toList()));

        addKeys(this.tag(BlockTags.DOORS), List.of(keyFor(ModBlocks.getInstance().getDoor())));
        addKeys(this.tag(BlockTags.DOORS), List.of(keyFor(ModBlocks.getInstance().getFancyDoor())));

        addKeys(this.tag(BlockTags.WOODEN_DOORS), List.of(keyFor(ModBlocks.getInstance().getDoor())));
        addKeys(this.tag(BlockTags.WOODEN_DOORS), List.of(keyFor(ModBlocks.getInstance().getFancyDoor())));

        addKeys(this.tag(BlockTags.STAIRS), List.of(keyFor(ModBlocks.getInstance().getStair())));
        addKeys(this.tag(BlockTags.STAIRS), ModBlocks.getInstance().getAllBrickStairBlocks().stream()
            .map(GlobalTagProvider::keyFor).collect(Collectors.toList()));
    }

    private static ResourceKey<Block> keyFor(final Block block) {
        return ResourceKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(block));
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Global Tag Provider";
    }
}
