package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WallComponentTagProvider extends BlockTagsProvider
{
    public WallComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.WALL_MATERIALS)
                .addTags(ModTags.GLOBAL_DEFAULT_EXCLUDE_VANILLA_VARIANTS)
                // Include everything from GLOBAL_DEFAULT that has no stairs recipe
                .add(
                        Blocks.DEEPSLATE,
                        Blocks.TUFF,
                        Blocks.NETHERRACK,
                        Blocks.BASALT,
                        Blocks.BAMBOO_MOSAIC,
                        Blocks.CUT_RED_SANDSTONE,
                        Blocks.CUT_SANDSTONE,
                        Blocks.DARK_PRISMARINE,
                        Blocks.POLISHED_ANDESITE,
                        Blocks.POLISHED_DIORITE,
                        Blocks.POLISHED_GRANITE,
                        Blocks.PRISMARINE_BRICKS,
                        Blocks.PURPUR_BLOCK,
                        Blocks.QUARTZ_BLOCK,
                        Blocks.SMOOTH_QUARTZ,
                        Blocks.SMOOTH_RED_SANDSTONE,
                        Blocks.SMOOTH_SANDSTONE,
                        Blocks.SMOOTH_STONE,
                        Blocks.STONE
                )
                .addTags(ModTags.COPPER);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Wall Tag Provider";
    }
}
