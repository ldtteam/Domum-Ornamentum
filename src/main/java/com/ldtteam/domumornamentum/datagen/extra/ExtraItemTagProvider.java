package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExtraItemTagProvider extends ItemTagsProvider
{
    public ExtraItemTagProvider(
      final DataGenerator dataGenerator,
      final BlockTagsProvider tagsProvider,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(dataGenerator, tagsProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        copy(ModTags.EXTRA_BLOCKS, ModTags.EXTRA_BLOCK_ITEMS);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Item Tag Provider";
    }
}
