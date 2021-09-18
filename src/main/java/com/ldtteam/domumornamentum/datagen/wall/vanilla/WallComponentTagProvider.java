package com.ldtteam.domumornamentum.datagen.wall.vanilla;

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

public class WallComponentTagProvider extends BlockTagsProvider
{
    public WallComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {

        this.tag(ModTags.WALL_MATERIALS)
          .add(
            Blocks.POLISHED_ANDESITE,
            Blocks.POLISHED_DIORITE,
            Blocks.POLISHED_GRANITE
          )
          .addTags(
            ModTags.GLOBAL_DEFAULT
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Wall Tag Provider";
    }
}
