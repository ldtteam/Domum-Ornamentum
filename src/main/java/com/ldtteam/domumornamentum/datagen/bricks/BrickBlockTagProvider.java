package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrickBlockTagProvider extends BlockTagsProvider
{
    public BrickBlockTagProvider(final DataGenerator generatorIn, @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        this.tag(ModTags.BRICKS)
          .add(IModBlocks.getInstance().getBricks().toArray(Block[]::new))
          .add(IModBlocks.getInstance().getExtraTopBlocks().toArray(Block[]::new));
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Blocks Tag Provider";
    }
}
