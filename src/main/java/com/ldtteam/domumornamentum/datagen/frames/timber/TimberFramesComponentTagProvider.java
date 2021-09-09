package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TimberFramesComponentTagProvider extends BlockTagsProvider
{
    public TimberFramesComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {

        this.tag(ModTags.TIMBERFRAMES_FRAME)
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
            Tags.Blocks.OBSIDIAN,
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS
          );

        this.tag(ModTags.TIMBERFRAMES_CENTER)
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
            Blocks.WAXED_WEATHERED_CUT_COPPER,
            Blocks.BRICKS
          )
          .addTags(
            BlockTags.LOGS,
            BlockTags.PLANKS,
            BlockTags.CORAL_BLOCKS,
            BlockTags.WART_BLOCKS,
            Tags.Blocks.COBBLESTONE,
            Tags.Blocks.STONE,
            Tags.Blocks.END_STONES,
            Tags.Blocks.NETHERRACK,
            Tags.Blocks.OBSIDIAN,
            Tags.Blocks.STORAGE_BLOCKS,
            Tags.Blocks.SANDSTONE,
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS
          );

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Timber Frames Tag Provider";
    }
}
