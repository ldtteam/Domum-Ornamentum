package com.ldtteam.domumornamentum.datagen.frames.light;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FramedLightComponentTagProvider extends BlockTagsProvider
{
    public FramedLightComponentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.FRAMED_LIGHT_CENTER)
          .add(
            Blocks.GLOWSTONE,
            Blocks.SEA_LANTERN,
            Blocks.OCHRE_FROGLIGHT,
            Blocks.PEARLESCENT_FROGLIGHT,
            Blocks.VERDANT_FROGLIGHT,
            Blocks.SHROOMLIGHT
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Framed Light Tag Provider";
    }
}
