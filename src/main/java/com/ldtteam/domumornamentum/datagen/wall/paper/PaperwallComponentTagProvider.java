package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaperwallComponentTagProvider extends BlockTagsProvider
{
    public PaperwallComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        this.tag(ModTags.PAPERWALL_FRAME)
          .addTags(
            ModTags.GLOBAL_DEFAULT
          );

        this.tag(ModTags.PAPERWALL_CENTER)
          .addTags(
            ModTags.GLOBAL_DEFAULT
          );

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Paperwall Tag Provider";
    }
}
