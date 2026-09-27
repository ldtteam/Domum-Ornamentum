package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

public class SlabCompatibilityTagProvider implements BlockTagSection
{

    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {

        host.tag(BlockTags.SLABS)
          .add(
            ModBlocks.getInstance().getSlab()
          );

        host.tag(BlockTags.WOODEN_SLABS)
          .add(
            ModBlocks.getInstance().getSlab()
          );
    }

}
