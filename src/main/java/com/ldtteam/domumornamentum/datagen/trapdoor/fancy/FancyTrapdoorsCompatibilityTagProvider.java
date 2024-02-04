package com.ldtteam.domumornamentum.datagen.trapdoor.fancy;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FancyTrapdoorsCompatibilityTagProvider extends BlockTagsProvider
{
    public FancyTrapdoorsCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.TRAPDOORS)
          .add(
            ModBlocks.getInstance().getFancyTrapdoor()
          );

        this.tag(BlockTags.WOODEN_TRAPDOORS)
          .add(
            ModBlocks.getInstance().getFancyTrapdoor()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "FancyTrapdoor Compatibility Tag Provider";
    }
}
