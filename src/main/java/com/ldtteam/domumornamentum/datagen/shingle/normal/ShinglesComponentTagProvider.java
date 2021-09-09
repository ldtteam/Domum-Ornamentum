package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShinglesComponentTagProvider extends BlockTagsProvider
{
    public ShinglesComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {

        this.tag(ModTags.SHINGLES_ROOF)
          .add(
            Blocks.CLAY,
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
            Blocks.BLACK_TERRACOTTA,
            Blocks.BLACK_TERRACOTTA,
            Blocks.BLACKSTONE,
            Blocks.GILDED_BLACKSTONE,
            Blocks.HAY_BLOCK,
            Blocks.COPPER_BLOCK,
            Blocks.CUT_COPPER,
            Blocks.EXPOSED_COPPER,
            Blocks.OXIDIZED_COPPER,
            Blocks.WEATHERED_COPPER,
            Blocks.WEATHERED_CUT_COPPER,
            Blocks.WAXED_COPPER_BLOCK,
            Blocks.WAXED_CUT_COPPER,
            Blocks.WAXED_EXPOSED_COPPER,
            Blocks.WAXED_OXIDIZED_COPPER,
            Blocks.WAXED_WEATHERED_COPPER,
            Blocks.WAXED_WEATHERED_CUT_COPPER,
            Blocks.BRICKS
          )
          .addTags(
            BlockTags.LOGS,
            BlockTags.PLANKS,
            BlockTags.CORAL_BLOCKS,
            BlockTags.WART_BLOCKS,
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS,
            Tags.Blocks.SANDSTONE
          );

        this.tag(ModTags.SHINGLES_SUPPORT)
          .add(
            Blocks.COPPER_BLOCK,
            Blocks.CUT_COPPER,
            Blocks.EXPOSED_COPPER,
            Blocks.OXIDIZED_COPPER,
            Blocks.WEATHERED_COPPER,
            Blocks.WEATHERED_CUT_COPPER,
            Blocks.WAXED_COPPER_BLOCK,
            Blocks.WAXED_CUT_COPPER,
            Blocks.WAXED_EXPOSED_COPPER,
            Blocks.WAXED_OXIDIZED_COPPER,
            Blocks.WAXED_WEATHERED_COPPER,
            Blocks.WAXED_WEATHERED_CUT_COPPER
          )
          .addTags(
            BlockTags.LOGS,
            BlockTags.PLANKS,
            Tags.Blocks.STONE,
            Tags.Blocks.END_STONES,
            Tags.Blocks.NETHERRACK,
            Tags.Blocks.OBSIDIAN,
            ModTags.BRICKS,
            Tags.Blocks.STORAGE_BLOCKS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingles Tag Provider";
    }
}
