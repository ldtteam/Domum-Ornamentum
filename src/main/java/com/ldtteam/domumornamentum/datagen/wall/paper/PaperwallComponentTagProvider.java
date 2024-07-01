package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PaperwallComponentTagProvider extends BlockTagsProvider
{
    public PaperwallComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(ModTags.PAPERWALL_FRAME)
          .addTags(
            BlockTags.PLANKS,
            ModTags.GLOBAL_DEFAULT
          );

        this.tag(ModTags.PAPERWALL_CENTER)
          .addTags(
            BlockTags.PLANKS,
            Tags.Blocks.STONES,
            ModTags.GLOBAL_DEFAULT
          );

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Paperwall Tag Provider";
    }
}
