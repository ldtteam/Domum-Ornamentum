package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BrickBlockTagProvider extends BlockTagsProvider
{

    public BrickBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable DatagenContext existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Blocks Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider holderLookupProvider) {
        final var brickTag = this.tag(ModTags.BRICKS);
        for (final Block block : IModBlocks.getInstance().getBricks())
        {
            brickTag.add(ResourceKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(block)));
        }
        for (final Block block : IModBlocks.getInstance().getExtraTopBlocks())
        {
            brickTag.add(ResourceKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(block)));
        }
    }
}
