package com.ldtteam.domumornamentum.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import com.ldtteam.domumornamentum.datagen.DatagenContext;

import java.util.concurrent.CompletableFuture;

public abstract class BlockTagsProvider extends TagsProvider<Block> {
    protected BlockTagsProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        String modId,
        DatagenContext existingFileHelper
    ) {
        this(output, lookupProvider, CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()), modId, existingFileHelper);
    }

    protected BlockTagsProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        CompletableFuture<TagsProvider.TagLookup<Block>> parentProvider,
        String modId,
        DatagenContext existingFileHelper
    ) {
        super(output, Registries.BLOCK, lookupProvider, parentProvider, modId);
    }
}
