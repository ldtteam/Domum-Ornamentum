package com.ldtteam.domumornamentum.datagen.pillar;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PillarComponentTagProvider extends BlockTagsProvider
{
    public PillarComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        this.tag(ModTags.PILLAR_MATERIALS)
          .add(
            Blocks.CLAY,
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          )
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.LEAVES,
            BlockTags.PLANKS,
            BlockTags.DIRT
          );

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Pillar Tag Provider";
    }
}
