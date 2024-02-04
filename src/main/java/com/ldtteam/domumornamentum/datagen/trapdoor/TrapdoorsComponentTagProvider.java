package com.ldtteam.domumornamentum.datagen.trapdoor;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TrapdoorsComponentTagProvider extends BlockTagsProvider
{
    public TrapdoorsComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(ModTags.TRAPDOORS_MATERIALS)
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS,
            ModTags.GLACED_TERRACOTTA
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Trapdoors Tag Provider";
    }
}
