package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ExtraItemTagProvider extends ItemTagsProvider
{

    public ExtraItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture) {
        super(packOutput, providerCompletableFuture, Constants.MOD_ID);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Item Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (final Block block : ModBlocks.getInstance().getExtraTopBlocks())
        {
            this.tag(ModTags.EXTRA_BLOCK_ITEMS).add(block.asItem());
        }
    }

    public static void register(GatherDataEvent.Server event)
    {
        event.addProvider(new ExtraItemTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider()));
    }

}
