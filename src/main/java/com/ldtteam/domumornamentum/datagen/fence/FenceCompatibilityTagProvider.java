package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.domumornamentum.block.ModBlocks;
import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FenceCompatibilityTagProvider extends BlockTagsProvider
{
    public FenceCompatibilityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable DatagenContext existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        final var tagVar1 = this.tag(BlockTags.FENCES);
        addBlocks(tagVar1,
            ModBlocks.getInstance().getFence()
          );

        final var tagVar2 = this.tag(BlockTags.WOODEN_FENCES);
        addBlocks(tagVar2,
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
