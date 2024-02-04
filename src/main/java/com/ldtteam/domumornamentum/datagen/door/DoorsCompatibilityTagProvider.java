package com.ldtteam.domumornamentum.datagen.door;

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

public class DoorsCompatibilityTagProvider extends BlockTagsProvider
{


    public DoorsCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Door Compatibility Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.DOORS)
                .add(
                        ModBlocks.getInstance().getDoor()
                );

        this.tag(BlockTags.WOODEN_DOORS)
                .add(
                        ModBlocks.getInstance().getDoor()
                );
    }
}
