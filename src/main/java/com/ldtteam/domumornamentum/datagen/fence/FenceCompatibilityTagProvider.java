package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.domumornamentum.block.ModBlocks;
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

public class FenceCompatibilityTagProvider extends BlockTagsProvider
{
    public FenceCompatibilityTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {

        this.tag(BlockTags.FENCES)
          .add(
            ModBlocks.getInstance().getFence()
          );

        this.tag(BlockTags.WOODEN_FENCES)
          .add(
            ModBlocks.getInstance().getFence()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Fence Tag Provider";
    }
}
