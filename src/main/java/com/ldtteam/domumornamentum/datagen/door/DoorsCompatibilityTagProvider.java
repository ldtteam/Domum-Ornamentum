package com.ldtteam.domumornamentum.datagen.door;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DoorsCompatibilityTagProvider extends BlockTagsProvider
{
    public DoorsCompatibilityTagProvider(
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
            ModBlocks.getInstance().getDoor()
          );

        this.tag(BlockTags.WOODEN_DOORS)
          .add(
            ModBlocks.getInstance().getDoor()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Door Tag Provider";
    }
}
