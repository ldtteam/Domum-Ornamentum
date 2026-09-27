package com.ldtteam.domumornamentum.datagen.trapdoor.fancy;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

public class FancyTrapdoorsCompatibilityTagProvider implements BlockTagSection
{

    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {
        host.tag(BlockTags.TRAPDOORS)
          .add(
            ModBlocks.getInstance().getFancyTrapdoor()
          );

        host.tag(BlockTags.WOODEN_TRAPDOORS)
          .add(
            ModBlocks.getInstance().getFancyTrapdoor()
          );
    }

}
