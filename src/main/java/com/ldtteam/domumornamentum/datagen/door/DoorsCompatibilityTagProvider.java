package com.ldtteam.domumornamentum.datagen.door;

import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addBlocks;

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

public class DoorsCompatibilityTagProvider extends BlockTagsProvider
{


    public DoorsCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable DatagenContext existingFileHelper) {
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
        addBlocks(this.tag(BlockTags.DOORS),
                        ModBlocks.getInstance().getDoor()
                );

        addBlocks(this.tag(BlockTags.WOODEN_DOORS),
                        ModBlocks.getInstance().getDoor()
                );
    }
}
