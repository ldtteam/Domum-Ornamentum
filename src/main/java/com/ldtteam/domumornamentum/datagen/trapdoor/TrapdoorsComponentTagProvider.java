package com.ldtteam.domumornamentum.datagen.trapdoor;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

public class TrapdoorsComponentTagProvider implements BlockTagSection
{

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {

        host.tag(ModTags.TRAPDOORS_MATERIALS)
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS,
            ModTags.GLACED_TERRACOTTA
          );
    }

}
