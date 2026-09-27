package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import com.ldtteam.domumornamentum.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ExtraBlockTagProvider implements BlockTagSection
{

    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {
        for (final Block block : ModBlocks.getInstance().getExtraTopBlocks())
        {
            host.tag(ModTags.EXTRA_BLOCKS).add(block);
        }
    }

}
