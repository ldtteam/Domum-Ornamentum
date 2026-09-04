package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.domumornamentum.tag.ModTags;
import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TimberFramesComponentTagProvider extends BlockTagsProvider
{
    public TimberFramesComponentTagProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        CompletableFuture<TagsProvider.TagLookup<Block>> parentProvider,
        @Nullable DatagenContext existingFileHelper
    ) {
        super(output, lookupProvider, parentProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        final var tagVar1 = this.tag(ModTags.TIMBERFRAMES_FRAME);
        addBlocks(tagVar1,
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          );
        tagVar1.addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS,
            Tags.Blocks.OBSIDIANS,
            Tags.Blocks.STONES
          );

        final var tagVar2 = this.tag(ModTags.TIMBERFRAMES_CENTER);
        addBlocks(tagVar2,
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          );
        tagVar2.addTags(
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

    @Override
    @NotNull
    public String getName()
    {
        return "Timber Frames Tag Provider";
    }
}
