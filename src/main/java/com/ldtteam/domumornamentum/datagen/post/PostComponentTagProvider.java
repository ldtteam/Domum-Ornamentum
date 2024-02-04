package com.ldtteam.domumornamentum.datagen.post;

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

public class PostComponentTagProvider extends BlockTagsProvider
{
    public PostComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {


        /*
          Exactly as others.  FUTURE, would like to allow the cutter to make slabs with vanilla materials, so those can also be placed sideways
         */
        this.tag(ModTags.POST_MATERIALS)

            .addTags(
                    ModTags.GLOBAL_DEFAULT,
                    BlockTags.PLANKS
            );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Post Tag Provider";
    }
}
