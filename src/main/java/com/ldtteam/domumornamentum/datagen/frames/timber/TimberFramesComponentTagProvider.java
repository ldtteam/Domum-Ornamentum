package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.datagen.global.BlockTagSection;
import com.ldtteam.domumornamentum.datagen.global.ModBlockTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class TimberFramesComponentTagProvider implements BlockTagSection
{

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider provider) {
        host.tag(ModTags.TIMBERFRAMES_FRAME)
          .add(
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          )
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS,
            Tags.Blocks.OBSIDIANS,
            Tags.Blocks.STONES
          );

        host.tag(ModTags.TIMBERFRAMES_CENTER)
          .add(
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          )
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS,
            Tags.Blocks.COBBLESTONES,
            Tags.Blocks.STONES,
            Tags.Blocks.END_STONES,
            Tags.Blocks.NETHERRACKS,
            Tags.Blocks.OBSIDIANS,
            Tags.Blocks.SANDSTONE_BLOCKS,
            BlockTags.DIRT
          );

    }

}
