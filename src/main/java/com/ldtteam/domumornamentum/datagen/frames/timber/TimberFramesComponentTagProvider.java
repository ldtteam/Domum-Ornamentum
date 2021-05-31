package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
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
    protected void registerTags()
    {

        this.getOrCreateBuilder(ModTags.TIMBERFRAMES_FRAME)
          .addTags(
            BlockTags.LOGS,
            BlockTags.PLANKS,
            Tags.Blocks.OBSIDIAN
          );

        this.getOrCreateBuilder(ModTags.TIMBERFRAMES_CENTER)
          .addTags(
            BlockTags.LOGS,
            BlockTags.PLANKS,
            BlockTags.CORAL_BLOCKS,
            BlockTags.WART_BLOCKS,
            Tags.Blocks.STONE,
            Tags.Blocks.END_STONES,
            Tags.Blocks.NETHERRACK,
            Tags.Blocks.OBSIDIAN,
            Tags.Blocks.STORAGE_BLOCKS
          );

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Timber Frames Tag Provider";
    }
}
