package com.ldtteam.domumornamentum.datagen.compat.create;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CreateCompatTagProvider extends BlockTagsProvider {
    private static TagKey<Block> SAFE_NBT = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("create", "safe_nbt"));

    public CreateCompatTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(SAFE_NBT)
                .addTag(ModTags.DYNAMIC_BLOCKS);
    }
}