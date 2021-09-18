package com.ldtteam.domumornamentum.datagen.stair;

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

public class StairsComponentTagProvider extends BlockTagsProvider
{
    public StairsComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {

        this.tag(ModTags.STAIRS_MATERIALS)
          .add(
            Blocks.BLACKSTONE,
            Blocks.GILDED_BLACKSTONE,
            Blocks.NETHERRACK,
            Blocks.CRIMSON_NYLIUM,
            Blocks.WARPED_NYLIUM,
            Blocks.BASALT,
            Blocks.POLISHED_BASALT,
            Blocks.SMOOTH_BASALT,
            Blocks.HAY_BLOCK,
            Blocks.COPPER_BLOCK,
            Blocks.CUT_COPPER,
            Blocks.EXPOSED_COPPER,
            Blocks.OXIDIZED_COPPER,
            Blocks.WEATHERED_COPPER,
            Blocks.WAXED_COPPER_BLOCK,
            Blocks.WAXED_EXPOSED_COPPER,
            Blocks.WAXED_OXIDIZED_COPPER,
            Blocks.WAXED_WEATHERED_COPPER
          )
          .addTags(
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS,
            ModTags.CONCRETE,
            ModTags.TERRACOTTA,
            BlockTags.DIRT,
            BlockTags.WOOL,
            BlockTags.LEAVES,
            Tags.Blocks.STORAGE_BLOCKS,
            Tags.Blocks.GLASS,
            BlockTags.LOGS,
            BlockTags.CORAL_BLOCKS,
            BlockTags.WART_BLOCKS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Stairs Tag Provider";
    }
}
