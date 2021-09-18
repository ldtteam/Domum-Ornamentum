package com.ldtteam.domumornamentum.datagen.door;

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

public class DoorsComponentTagProvider extends BlockTagsProvider
{
    public DoorsComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        this.tag(ModTags.DOORS_MATERIALS)
          .addTags(
            ModTags.GLOBAL_DEFAULT
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Doors Tag Provider";
    }
}
