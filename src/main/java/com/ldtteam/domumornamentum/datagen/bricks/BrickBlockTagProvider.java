package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import com.ldtteam.domumornamentum.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class BrickBlockTagProvider implements BlockTagSection
{

    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider holderLookupProvider) {
        host.tag(ModTags.BRICKS)
                .add(IModBlocks.getInstance().getBricks().toArray(Block[]::new))
                .add(IModBlocks.getInstance().getExtraTopBlocks().toArray(Block[]::new));
    }

}
