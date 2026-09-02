package com.ldtteam.domumornamentum.datagen.fence;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FenceComponentTagProvider extends BlockTagsProvider
{
    public FenceComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.FENCE_MATERIALS)
                .addTags(ModTags.GLOBAL_DEFAULT_EXCLUDE_VANILLA_VARIANTS)
                // Include everything from GLOBAL_DEFAULT that has no fence recipe
                .add(
                        // Overworld base
                        Blocks.BRICKS,
                        Blocks.MUD_BRICKS,
                        Blocks.BAMBOO_MOSAIC,
                        // Stones
                        Blocks.SMOOTH_STONE,
                        Blocks.POLISHED_ANDESITE,
                        Blocks.POLISHED_DIORITE,
                        Blocks.POLISHED_GRANITE,
                        // Deepslate
                        Blocks.POLISHED_DEEPSLATE,
                        Blocks.DEEPSLATE_BRICKS,
                        Blocks.DEEPSLATE_TILES,
                        // Primsarine
                        Blocks.PRISMARINE,
                        Blocks.PRISMARINE_BRICKS,
                        Blocks.DARK_PRISMARINE,

                        // Blackstone
                        Blocks.POLISHED_BLACKSTONE,
                        Blocks.POLISHED_BLACKSTONE_BRICKS,
                        // Quartz
                        Blocks.QUARTZ_BLOCK,
                        Blocks.SMOOTH_QUARTZ,

                        // End base
                        Blocks.END_STONE_BRICKS,
                        // Purpur
                        Blocks.PURPUR_BLOCK
                )
                .addTags(
                        ModTags.GLOBAL_DEFAULT_EXCLUDE_VANILLA_VARIANTS,
                        BlockTags.BASE_STONE_OVERWORLD,
                        BlockTags.BASE_STONE_NETHER,
                        BlockTags.STONE_BRICKS,
                        Tags.Blocks.COBBLESTONE_NORMAL,
                        Tags.Blocks.COBBLESTONE_DEEPSLATE,
                        Tags.Blocks.COBBLESTONE_MOSSY,
                        Tags.Blocks.STONE,
                        Tags.Blocks.SANDSTONE
                );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Fence Tag Provider";
    }
}
