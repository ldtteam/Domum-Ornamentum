package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlabCompatibilityTagProvider extends BlockTagsProvider
{
    public SlabCompatibilityTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {

        this.tag(BlockTags.SLABS)
          .add(
            ModBlocks.getInstance().getSlab()
          );

        this.tag(BlockTags.WOODEN_SLABS)
          .add(
            ModBlocks.getInstance().getSlab()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Slab Tag Provider";
    }
}
