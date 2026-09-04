package com.ldtteam.domumornamentum.datagen.pillar;

import com.ldtteam.domumornamentum.tag.ModTags;
import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PillarComponentTagProvider extends BlockTagsProvider
{
    public PillarComponentTagProvider(
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
        final var tagVar1 = this.tag(ModTags.PILLAR_MATERIALS);
        addBlocks(tagVar1,
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          );
        tagVar1.addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Pillar Tag Provider";
    }
}
