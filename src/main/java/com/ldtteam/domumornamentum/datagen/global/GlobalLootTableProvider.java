package com.ldtteam.domumornamentum.datagen.global;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.datagen.loot.MaterialLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * This class generates the default loot_table for blocks (if a block is destroyed, it drops its item).
 */
public class GlobalLootTableProvider extends LootTableProvider
{

    public GlobalLootTableProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput,
            Set.of(),
            List.of(new SubProviderEntry(GlobalLootTableEntries::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(MaterialLootTableProvider::new, LootContextParamSets.BLOCK)),
            provider);
    }

    private static final class GlobalLootTableEntries extends BlockLootSubProvider {

        private GlobalLootTableEntries(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected void generate() {
            for (final BrickBlock block : ModBlocks.getInstance().getBricks())
            {
                validateBlockItem(block);
                dropSelf(block);
            }

            for (final ExtraBlock block : ModBlocks.getInstance().getExtraTopBlocks())
            {
                validateBlockItem(block);
                dropSelf(block);
            }

            for (final FloatingCarpetBlock block : ModBlocks.getInstance().getFloatingCarpets())
            {
                validateBlockItem(block);
                dropSelf(block);
            }

            validateBlockItem(ModBlocks.getInstance().getStandingBarrel());
            dropSelf(ModBlocks.getInstance().getStandingBarrel());
            validateBlockItem(ModBlocks.getInstance().getLayingBarrel());
            dropSelf(ModBlocks.getInstance().getLayingBarrel());
            validateBlockItem(ModBlocks.getInstance().getArchitectsCutter());
            dropSelf(ModBlocks.getInstance().getArchitectsCutter());
        }

        private static void validateBlockItem(final Block block) {
            if (block.asItem() == net.minecraft.world.item.Items.AIR) {
                throw new IllegalStateException("Block has no BlockItem: " + BuiltInRegistries.BLOCK.getKey(block));
            }
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return ImmutableList.<Block>builder()
                    .addAll(ModBlocks.getInstance().getBricks())
                    .addAll(ModBlocks.getInstance().getExtraTopBlocks())
                    .addAll(ModBlocks.getInstance().getFloatingCarpets())
                    .add(ModBlocks.getInstance().getStandingBarrel())
                    .add(ModBlocks.getInstance().getLayingBarrel())
                    .add(ModBlocks.getInstance().getArchitectsCutter()).build();
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Default Block Loot Tables Provider";
    }
}
