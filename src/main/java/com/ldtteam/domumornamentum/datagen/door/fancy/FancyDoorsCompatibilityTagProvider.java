package com.ldtteam.domumornamentum.datagen.door.fancy;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

public class FancyDoorsCompatibilityTagProvider implements BlockTagSection
{

    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {
        host.tag(BlockTags.DOORS)
          .add(
            ModBlocks.getInstance().getFancyDoor()
          );

        host.tag(BlockTags.WOODEN_DOORS)
          .add(
            ModBlocks.getInstance().getFancyDoor()
          );
    }

}
