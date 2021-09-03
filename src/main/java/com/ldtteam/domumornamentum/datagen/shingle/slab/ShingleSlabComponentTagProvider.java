package com.ldtteam.domumornamentum.datagen.shingle.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShingleSlabComponentTagProvider extends BlockTagsProvider
{
    public ShingleSlabComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        this.tag(ModTags.SHINGLES_COVER)
          .add(
            Blocks.COPPER_BLOCK,
            Blocks.CUT_COPPER,
            Blocks.EXPOSED_COPPER,
            Blocks.OXIDIZED_COPPER,
            Blocks.WEATHERED_COPPER,
            Blocks.WEATHERED_CUT_COPPER,
            Blocks.WAXED_COPPER_BLOCK,
            Blocks.WAXED_CUT_COPPER,
            Blocks.WAXED_EXPOSED_COPPER,
            Blocks.WAXED_OXIDIZED_COPPER,
            Blocks.WAXED_WEATHERED_COPPER,
            Blocks.WAXED_WEATHERED_CUT_COPPER
          )
          .addTags(
            BlockTags.LOGS,
            BlockTags.PLANKS,
            Tags.Blocks.STONE,
            Tags.Blocks.END_STONES,
            Tags.Blocks.NETHERRACK,
            Tags.Blocks.OBSIDIAN,
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS,
            ModTags.SHINGLES_ROOF
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingle Slabs Tag Provider";
    }
}
