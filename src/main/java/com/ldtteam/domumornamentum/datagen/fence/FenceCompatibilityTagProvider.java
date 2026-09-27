package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

public class FenceCompatibilityTagProvider implements BlockTagSection
{

    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {

        host.tag(BlockTags.FENCES)
          .add(
            ModBlocks.getInstance().getFence()
          );

        host.tag(BlockTags.WOODEN_FENCES)
          .add(
            ModBlocks.getInstance().getFence()
          );
    }

}
