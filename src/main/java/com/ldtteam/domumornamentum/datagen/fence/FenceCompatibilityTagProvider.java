package com.ldtteam.domumornamentum.datagen.fence;

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

public class FenceCompatibilityTagProvider extends BlockTagsProvider
{
    public FenceCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(BlockTags.FENCES)
          .add(
            ModBlocks.getInstance().getFence()
          );

        this.tag(BlockTags.WOODEN_FENCES)
          .add(
            ModBlocks.getInstance().getFence()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Fence Compatibility Tag Provider";
    }
}
