package com.ldtteam.domumornamentum.datagen.door.fancy;

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

public class FancyDoorsCompatibilityTagProvider extends BlockTagsProvider
{
    public FancyDoorsCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider)
    {
        this.tag(BlockTags.DOORS)
          .add(
            ModBlocks.getInstance().getFancyDoor()
          );

        this.tag(BlockTags.WOODEN_DOORS)
          .add(
            ModBlocks.getInstance().getFancyDoor()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Fancy Doors Compatibility Tag Provider";
    }
}
