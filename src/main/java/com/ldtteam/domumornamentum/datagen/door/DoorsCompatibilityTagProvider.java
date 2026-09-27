package com.ldtteam.domumornamentum.datagen.door;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jspecify.annotations.NonNull;

public class DoorsCompatibilityTagProvider implements BlockTagSection
{

    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NonNull Provider provider) {
        host.tag(BlockTags.DOORS)
                .add(
                        ModBlocks.getInstance().getDoor()
                );

        host.tag(BlockTags.WOODEN_DOORS)
                .add(
                        ModBlocks.getInstance().getDoor()
                );
    }

}
