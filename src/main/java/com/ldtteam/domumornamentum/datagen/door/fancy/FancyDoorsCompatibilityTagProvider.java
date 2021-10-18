package com.ldtteam.domumornamentum.datagen.door.fancy;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FancyDoorsCompatibilityTagProvider extends BlockTagsProvider
{
    public FancyDoorsCompatibilityTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        this.tag(BlockTags.DOORS)
          .add(
            ModBlocks.getInstance().getFancyDoor()
          );

        this.tag(BlockTags.WOODEN_DOORS)
          .add(
            ModBlocks.getInstance().getFancyDoor()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Fancy Doors Compatibility Tag Provider";
    }
}
