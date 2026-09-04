package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WallCompatibilityTagProvider extends BlockTagsProvider
{
    public WallCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable DatagenContext existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {


        addBlocks(this.tag(BlockTags.WALLS), ModBlocks.getInstance().getWall());
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Wall Compatibility Tag Provider";
    }

    private void addBlocks(final net.minecraft.data.tags.TagAppender<net.minecraft.world.level.block.Block> appender, final net.minecraft.world.level.block.Block... blocks)
    {
        for (final var block : blocks)
        {
            appender.add(net.minecraft.resources.ResourceKey.create(
                net.minecraft.core.registries.Registries.BLOCK,
                net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block)));
        }
    }

}
