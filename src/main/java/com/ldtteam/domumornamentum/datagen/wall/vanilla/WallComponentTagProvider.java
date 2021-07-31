package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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
            Blocks.CLAY,
            Blocks.TERRACOTTA,
            Blocks.WHITE_TERRACOTTA,
            Blocks.ORANGE_TERRACOTTA,
            Blocks.MAGENTA_TERRACOTTA,
            Blocks.LIGHT_BLUE_TERRACOTTA,
            Blocks.YELLOW_TERRACOTTA,
            Blocks.LIME_TERRACOTTA,
            Blocks.PINK_TERRACOTTA,
            Blocks.GRAY_TERRACOTTA,
            Blocks.LIGHT_GRAY_TERRACOTTA,
            Blocks.CYAN_TERRACOTTA,
            Blocks.PURPLE_TERRACOTTA,
            Blocks.BLUE_TERRACOTTA,
            Blocks.BROWN_TERRACOTTA,
            Blocks.GREEN_TERRACOTTA,
            Blocks.RED_TERRACOTTA,
            Blocks.BLACK_TERRACOTTA,
            Blocks.BLACK_TERRACOTTA,
            Blocks.BLACKSTONE,
            Blocks.GILDED_BLACKSTONE,
            Blocks.HAY_BLOCK
          )
          .addTags(
            BlockTags.LOGS,
            BlockTags.CORAL_BLOCKS,
            BlockTags.WART_BLOCKS,
            BlockTags.LOGS,
            BlockTags.PLANKS,
            ModTags.EXTRA_BLOCKS,
            ModTags.BRICKS
          );

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Wall Tag Provider";
    }
}
