package com.ldtteam.domumornamentum.datagen.trapdoor;

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

public class TrapdoorsCompatibilityTagProvider extends BlockTagsProvider
{
    public TrapdoorsCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(BlockTags.TRAPDOORS)
          .add(
            ModBlocks.getInstance().getTrapdoor()
          );

        this.tag(BlockTags.WOODEN_TRAPDOORS)
          .add(
            ModBlocks.getInstance().getTrapdoor()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Trapdoor Compatibility Tag Provider";
    }
}
