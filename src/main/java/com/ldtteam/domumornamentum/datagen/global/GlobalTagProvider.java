package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GlobalTagProvider extends BlockTagsProvider
{
    public GlobalTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
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

        this.tag(ModTags.TERRACOTTA).add(
          Blocks.TERRACOTTA,
          Blocks.WHITE_TERRACOTTA,
          Blocks.ORANGE_TERRACOTTA,
          Blocks.MAGENTA_TERRACOTTA,
          Blocks.LIGHT_BLUE_TERRACOTTA,
          Blocks.YELLOW_TERRACOTTA,
          Blocks.LIME_TERRACOTTA,
          Blocks.PINK_TERRACOTTA,
          Blocks.GRAY_TERRACOTTA,
          Blocks.LIGHT_GRAY_TERRACOTTA,
          Blocks.CYAN_TERRACOTTA,
          Blocks.PURPLE_TERRACOTTA,
          Blocks.BLUE_TERRACOTTA,
          Blocks.BROWN_TERRACOTTA,
          Blocks.GREEN_TERRACOTTA,
          Blocks.RED_TERRACOTTA,
          Blocks.BLACK_TERRACOTTA);

        this.tag(ModTags.COPPER).add(
          Blocks.COPPER_BLOCK,
          Blocks.WAXED_COPPER_BLOCK,
          Blocks.EXPOSED_COPPER,
          Blocks.WAXED_EXPOSED_COPPER,
          Blocks.WEATHERED_COPPER,
          Blocks.WAXED_WEATHERED_COPPER,
          Blocks.OXIDIZED_COPPER,
          Blocks.WAXED_OXIDIZED_COPPER,
          Blocks.WAXED_CUT_COPPER,
          Blocks.EXPOSED_CUT_COPPER,
          Blocks.WAXED_EXPOSED_CUT_COPPER,
          Blocks.WEATHERED_CUT_COPPER,
          Blocks.WAXED_WEATHERED_CUT_COPPER,
          Blocks.OXIDIZED_CUT_COPPER,
          Blocks.WAXED_OXIDIZED_CUT_COPPER);

        this.tag(ModTags.GLOBAL_DEFAULT).add(
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
          Blocks.END_STONE
        )
          .addTags(
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS,
            ModTags.CONCRETE,
            ModTags.TERRACOTTA,
            ModTags.COPPER,
            BlockTags.WOOL,
            Tags.Blocks.STORAGE_BLOCKS,
            Tags.Blocks.GLASS,
            BlockTags.LOGS,
            BlockTags.WART_BLOCKS
          );

        this.tag(BlockTags.MINEABLE_WITH_AXE)
          .add(ModBlocks.getInstance().getArchitectsCutter(),
            ModBlocks.getInstance().getLayingBarrel(),
            ModBlocks.getInstance().getStandingBarrel(),
            ModBlocks.getInstance().getShingle(),
            ModBlocks.getInstance().getShingleSlab())
        .add(ModBlocks.getInstance().getTimberFrames().toArray(new Block[0]));

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
          .addTags(ModTags.BRICKS,
            ModTags.EXTRA_BLOCKS);

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Global Tag Provider";
    }
}
