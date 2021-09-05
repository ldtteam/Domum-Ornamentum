package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExtraBlockTagProvider extends BlockTagsProvider
{
    public ExtraBlockTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        for (final Block block : ModBlocks.getInstance().getExtraTopBlocks())
        {
            this.tag(ModTags.EXTRA_BLOCKS).add(block);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Blocks Tag Provider";
    }
}
