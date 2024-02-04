package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ShinglesComponentTagProvider extends BlockTagsProvider
{
    public ShinglesComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.SHINGLES_ROOF)
          .add(
            Blocks.CLAY,
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          )
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.LEAVES,
            BlockTags.PLANKS,
            BlockTags.DIRT
          );

        this.tag(ModTags.SHINGLES_SUPPORT)
          .add(
            Blocks.DEEPSLATE,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          )
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingles Tag Provider";
    }
}
