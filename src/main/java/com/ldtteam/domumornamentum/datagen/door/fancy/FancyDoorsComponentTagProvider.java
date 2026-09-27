package com.ldtteam.domumornamentum.datagen.door.fancy;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

public class FancyDoorsComponentTagProvider implements BlockTagSection
{

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {
        host.tag(ModTags.FANCY_DOORS_MATERIALS)
          .addTags(
            ModTags.DOORS_MATERIALS
          );
    }

}
