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
        this.tag(ModTags.WALL_MATERIALS).add(
          Blocks.HAY_BLOCK,
          Blocks.BLACKSTONE,
          Blocks.GILDED_BLACKSTONE,
          Blocks.POLISHED_BLACKSTONE_BRICKS,
          Blocks.NETHERRACK,
          Blocks.CRIMSON_NYLIUM,
          Blocks.WARPED_NYLIUM,
          Blocks.BASALT,
          Blocks.POLISHED_BASALT,
          Blocks.SMOOTH_BASALT,
          Blocks.DEEPSLATE_BRICKS,
          Blocks.POLISHED_DEEPSLATE,
          Blocks.PURPUR_BLOCK,
          Blocks.PURPUR_PILLAR,
          Blocks.END_STONE,
          Blocks.OBSIDIAN,
          Blocks.AMETHYST_BLOCK,
          Blocks.BUDDING_AMETHYST,
          Blocks.PACKED_ICE,
          Blocks.SNOW_BLOCK,
          Blocks.CRACKED_STONE_BRICKS,
          Blocks.SMOOTH_STONE,
          Blocks.CHISELED_STONE_BRICKS,
          Blocks.POLISHED_ANDESITE,
          Blocks.POLISHED_DIORITE,
          Blocks.POLISHED_GRANITE
        )
          .addTags(
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS,
            ModTags.CONCRETE,
            ModTags.TERRACOTTA,
            ModTags.COPPER,
            BlockTags.WOOL,
            Tags.Blocks.STORAGE_BLOCKS,
            Tags.Blocks.GLASS,
            BlockTags.LOGS,
            BlockTags.WART_BLOCKS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Wall Tag Provider";
    }
}
