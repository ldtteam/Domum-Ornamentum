package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import static com.ldtteam.domumornamentum.datagen.TagAppenderHelper.addBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import com.ldtteam.domumornamentum.datagen.tags.BlockTagsProvider;
import com.ldtteam.domumornamentum.datagen.DatagenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ExtraBlockTagProvider extends BlockTagsProvider
{
    public ExtraBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable DatagenContext existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (final Block block : ModBlocks.getInstance().getExtraTopBlocks())
        {
            final var tagVar1 = this.tag(ModTags.EXTRA_BLOCKS);
        addBlocks(tagVar1,block);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Blocks Tag Provider";
    }
}
