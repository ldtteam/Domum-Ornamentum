package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SlabCompatibilityTagProvider extends BlockTagsProvider
{
    public SlabCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(BlockTags.SLABS)
          .add(
            ModBlocks.getInstance().getSlab()
          );

        this.tag(BlockTags.WOODEN_SLABS)
          .add(
            ModBlocks.getInstance().getSlab()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Slab Compatibility Tag Provider";
    }
}
