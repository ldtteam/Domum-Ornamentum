package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class PaperwallComponentTagProvider implements BlockTagSection
{

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {

        host.tag(ModTags.PAPERWALL_FRAME)
          .addTags(
            BlockTags.PLANKS,
            ModTags.GLOBAL_DEFAULT
          );

        host.tag(ModTags.PAPERWALL_CENTER)
          .addTags(
            BlockTags.PLANKS,
            Tags.Blocks.STONES,
            ModTags.GLOBAL_DEFAULT
          );

    }

}
