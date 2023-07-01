package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FloatingCarpetLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public FloatingCarpetLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        backingLangJson.put("block." + Constants.MOD_ID + ".black_floating_carpet", "Black Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".blue_floating_carpet", "Blue Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".brown_floating_carpet", "Brown Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".cyan_floating_carpet", "Cyan Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".gray_floating_carpet", "Gray Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".green_floating_carpet", "Green Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".light_blue_floating_carpet", "Light Blue Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".light_gray_floating_carpet", "Light Gray Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".lime_floating_carpet", "Lime Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".magenta_floating_carpet", "Magenta Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".orange_floating_carpet", "Orange Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".pink_floating_carpet", "Pink Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".purple_floating_carpet", "Purple Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".red_floating_carpet", "Red Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".white_floating_carpet", "White Floating Carpet");
        backingLangJson.put("block." + Constants.MOD_ID + ".yellow_floating_carpet", "Yellow Floating Carpet");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Floating Carpet Lang Provider";
    }
}
