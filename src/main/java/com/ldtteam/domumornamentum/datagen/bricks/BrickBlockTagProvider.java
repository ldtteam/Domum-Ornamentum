package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BrickBlockTagProvider extends BlockTagsProvider
{

    public BrickBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
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
        this.tag(ModTags.BRICKS)
                .add(IModBlocks.getInstance().getBricks().toArray(Block[]::new))
                .add(IModBlocks.getInstance().getExtraTopBlocks().toArray(Block[]::new));
    }
}
