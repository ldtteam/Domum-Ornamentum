package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ExtraItemTagProvider extends ItemTagsProvider
{

    public ExtraItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagLookup<Block>> blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, blockTagsProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Item Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        copy(ModTags.EXTRA_BLOCKS, ModTags.EXTRA_BLOCK_ITEMS);
    }
}
