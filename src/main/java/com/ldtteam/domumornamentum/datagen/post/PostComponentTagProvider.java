package com.ldtteam.domumornamentum.datagen.post;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

public class PostComponentTagProvider implements BlockTagSection
{

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {

        /*
          Exactly as others.  FUTURE, would like to allow the cutter to make slabs with vanilla materials, so those can also be placed sideways
         */
        host.tag(ModTags.POST_MATERIALS)

            .addTags(
                    ModTags.GLOBAL_DEFAULT,
                    BlockTags.PLANKS
            );
    }

}
