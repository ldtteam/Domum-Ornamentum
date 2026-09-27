package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.post.PostComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallComponentTagProvider;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The single block tag provider for the mod.
 * <p>
 * Every category of block tags is a {@link BlockTagSection} contributing to this one instance, so all
 * same-namespace tag references (e.g. #domum_ornamentum:bricks from inside #domum_ornamentum:default)
 * validate against the same builder map, as required by NeoForge's per-instance tag validation.
 */
public class ModBlockTagsProvider extends BlockTagsProvider
{

    private static final List<BlockTagSection> SECTIONS = List.of(
        // All brick & bricks
        new AllBrickBlockTagProvider(),
        new BrickBlockTagProvider(),
        // Extra blocks
        new ExtraBlockTagProvider(),
        // Timber frames & framed light
        new TimberFramesComponentTagProvider(),
        new FramedLightComponentTagProvider(),
        // Shingles
        new ShinglesComponentTagProvider(),
        new ShingleSlabComponentTagProvider(),
        // Paper wall
        new PaperwallComponentTagProvider(),
        // Fences & gates
        new FenceComponentTagProvider(),
        new FenceCompatibilityTagProvider(),
        new FenceGateComponentTagProvider(),
        new FenceGateCompatibilityTagProvider(),
        // Slabs
        new SlabComponentTagProvider(),
        new SlabCompatibilityTagProvider(),
        // Walls
        new WallComponentTagProvider(),
        new WallCompatibilityTagProvider(),
        // Stairs
        new StairsComponentTagProvider(),
        new StairsCompatibilityTagProvider(),
        // Trapdoors
        new TrapdoorsComponentTagProvider(),
        new TrapdoorsCompatibilityTagProvider(),
        new FancyTrapdoorsComponentTagProvider(),
        new FancyTrapdoorsCompatibilityTagProvider(),
        // Posts & pillars
        new PostComponentTagProvider(),
        new PillarComponentTagProvider(),
        // Doors
        new DoorsComponentTagProvider(),
        new DoorsCompatibilityTagProvider(),
        new FancyDoorsComponentTagProvider(),
        new FancyDoorsCompatibilityTagProvider(),
        // Floating carpets
        new FloatingCarpetBlockTagProvider()
    );

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    /**
     * Exposes tag creation to the individual {@link BlockTagSection}s, which live in other packages and
     * therefore cannot access the protected method directly.
     */
    @Override
    public TagAppender<Block, Block> tag(final TagKey<Block> key) {
        return super.tag(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (final BlockTagSection section : SECTIONS) {
            section.addTags(this, provider);
        }

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

        this.tag(ModTags.GLACED_TERRACOTTA).add(
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
            Blocks.WAXED_OXIDIZED_CUT_COPPER,
            Blocks.CHISELED_COPPER,
            Blocks.WAXED_CHISELED_COPPER,
            Blocks.EXPOSED_CHISELED_COPPER,
            Blocks.WAXED_EXPOSED_CHISELED_COPPER,
            Blocks.WEATHERED_CHISELED_COPPER,
            Blocks.WAXED_WEATHERED_CHISELED_COPPER,
            Blocks.OXIDIZED_CHISELED_COPPER,
            Blocks.WAXED_OXIDIZED_CHISELED_COPPER,
            Blocks.COPPER_GRATE,
            Blocks.WAXED_COPPER_GRATE,
            Blocks.EXPOSED_COPPER_GRATE,
            Blocks.WAXED_EXPOSED_COPPER_GRATE,
            Blocks.WEATHERED_COPPER_GRATE,
            Blocks.WAXED_WEATHERED_COPPER_GRATE,
            Blocks.OXIDIZED_COPPER_GRATE,
            Blocks.WAXED_OXIDIZED_COPPER_GRATE);

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
        return "Mod Block Tags Provider";
    }

    public static void register(GatherDataEvent.Server event)
    {
        event.addProvider(new ModBlockTagsProvider(event.getGenerator().getPackOutput(), event.getLookupProvider()));
    }

}
