package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class BrickItemTagProvider extends ItemTagsProvider
{
    public BrickItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture) {
        super(packOutput, providerCompletableFuture, Constants.MOD_ID);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Item Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.tag(ModTags.BRICK_ITEMS)
                .add(IModBlocks.getInstance().getBricks().stream().map(Block::asItem).toArray(Item[]::new))
                .add(IModBlocks.getInstance().getExtraTopBlocks().stream().map(Block::asItem).toArray(Item[]::new));
    }

    public static void register(GatherDataEvent.Server event)
    {
        event.addProvider(new BrickItemTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider()));
    }

}
