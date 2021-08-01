package com.ldtteam.domumornamentum.datagen.trapdoor;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrapdoorsCompatibilityTagProvider extends BlockTagsProvider
{
    public TrapdoorsCompatibilityTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        this.tag(BlockTags.TRAPDOORS)
          .add(
            ModBlocks.getTrapdoor()
          );

        this.tag(BlockTags.WOODEN_TRAPDOORS)
          .add(
            ModBlocks.getTrapdoor()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Trapdoor Tag Provider";
    }
}
