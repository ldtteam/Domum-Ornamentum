package com.ldtteam.domumornamentum.datagen.stair;

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

public class StairsComponentTagProvider extends BlockTagsProvider
{
    public StairsComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.STAIRS_MATERIALS)
                .addTags(ModTags.GLOBAL_DEFAULT_EXCLUDE_VANILLA_VARIANTS)
                // Include everything from GLOBAL_DEFAULT that has no stairs recipe
                .add(
                        Blocks.SMOOTH_STONE,
                        Blocks.DEEPSLATE,
                        Blocks.TUFF,
                        Blocks.NETHERRACK,
                        Blocks.BASALT,
                        Blocks.CUT_SANDSTONE,
                        Blocks.CUT_RED_SANDSTONE
                );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Stairs Tag Provider";
    }
}
