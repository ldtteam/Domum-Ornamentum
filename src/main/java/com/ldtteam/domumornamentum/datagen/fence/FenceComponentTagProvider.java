package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

public class FenceComponentTagProvider implements BlockTagSection
{

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {
        host.tag(ModTags.FENCE_MATERIALS)
          .addTags(
            ModTags.GLOBAL_DEFAULT
          );
    }

}
