package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GlobalLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public GlobalLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".timber_frames", "DO - Framed Blocks");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".shingles", "DO - Shingles");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".general", "Domum Ornamentum (DO)");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".paperwalls", "DO - Thin Framed Walls");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".shingle_slabs", "DO - Shingle Slabs");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".extra-blocks", "DO - Decorative Blocks");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".floating-carpets", "DO - Floating Carpets");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".fences", "DO - Fences");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".slabs", "DO - Slabs");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".walls", "DO - Walls");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".stairs", "DO - Stairs");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".doors", "DO - Doors");
        backingLangJson.put("block." + Constants.MOD_ID + ".architectscutter", "Architect's Cutter");
        backingLangJson.put(Constants.MOD_ID + ".architectscutter", "Architect's Cutter");
        backingLangJson.put(Constants.MOD_ID + ".origin.tooltip", "Crafted in the Architect's Cutter");
        backingLangJson.put(Constants.MOD_ID + ".block.format", "Material: %s");
        backingLangJson.put(ModBlocks.getInstance().getStandingBarrel().getDescriptionId(), "Standing Barrel");
        backingLangJson.put(ModBlocks.getInstance().getLayingBarrel().getDescriptionId(), "Laying Barrel");


        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".brick", "Bricks");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".door", "Doors");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".trapdoor", "Trapdoors");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".light", "Lights");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".panel", "Panels");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".paperwall", "Paperwalls");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".pillar", "Pillars");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".post", "Posts");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".shingle", "Shingles");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".shingle_slab", "Shingleslabs");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".timberframe", "Timberframes");
        backingLangJson.put("cuttergroup." + Constants.MOD_ID + ".vanilla", "Vanilla Blocks");

        backingLangJson.put(Constants.MOD_ID + ".group", "Group:");
        backingLangJson.put(Constants.MOD_ID + ".variant", "Variant:");

        backingLangJson.put(Constants.MOD_ID + ".desc.material", "Material: %s");
        backingLangJson.put(Constants.MOD_ID + ".desc.main", "Main %s");
        backingLangJson.put(Constants.MOD_ID + ".desc.support", "Support %s");
        backingLangJson.put(Constants.MOD_ID + ".desc.center", "Center %s");
        backingLangJson.put(Constants.MOD_ID + ".desc.frame", "Frame %s");
        backingLangJson.put(Constants.MOD_ID + ".desc.shingle", "Shingle %s");
        backingLangJson.put(Constants.MOD_ID + ".desc.onlyone", "%s");
        
        
        DataProvider.saveStable(cache, backingLangJson.serialize(), dataGenerator.getOutputFolder().resolve(DataGeneratorConstants.EN_US_LANG));
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Global Lang Provider";
    }
}
