package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ExtraBlockTagProvider extends BlockTagsProvider
{
    public ExtraBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (final Block block : ModBlocks.getInstance().getExtraTopBlocks())
        {
            this.tag(ModTags.EXTRA_BLOCKS).add(block);
        }
        for (final Block block : ModBlocks.getInstance().getExtraTopBlocks())
        {
            this.tag(ModTags.EXTRA_DYED_BLOCKS).add(block);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Blocks Tag Provider";
    }
}
