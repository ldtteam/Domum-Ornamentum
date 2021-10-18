package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FenceGateComponentTagProvider extends BlockTagsProvider
{
    public FenceGateComponentTagProvider(
      final DataGenerator generatorIn,
      @Nullable final ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags()
    {
        this.tag(ModTags.FENCE_GATE_MATERIALS)
          .addTags(
            ModTags.FENCE_MATERIALS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "FenceGate Tag Provider";
    }
}
