package com.ldtteam.domumornamentum.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import com.ldtteam.domumornamentum.datagen.DatagenContext;

import java.util.concurrent.CompletableFuture;

public abstract class ItemTagsProvider extends TagsProvider<Item> {
    protected ItemTagsProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        CompletableFuture<TagsProvider.TagLookup<Block>> blockTags,
        String modId,
        DatagenContext existingFileHelper
    ) {
        super(output, Registries.ITEM, lookupProvider, modId);
        this.blockTags = blockTags;
    }

    private final CompletableFuture<TagsProvider.TagLookup<Block>> blockTags;

    protected void copy(TagKey<Block> source, TagKey<Item> destination) {
        TagBuilder target = getOrCreateRawBuilder(destination);
        blockTags.join().apply(source).ifPresent(sourceBuilder -> sourceBuilder.build().forEach(target::add));
    }
}
