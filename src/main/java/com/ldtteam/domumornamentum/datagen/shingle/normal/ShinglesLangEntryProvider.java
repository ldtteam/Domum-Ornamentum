package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ShinglesLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public ShinglesLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".shingle.name.format.block.domum_ornamentum.shingle_flat_lower", "%s Flat Lower Shingles");
        backingLangJson.put(Constants.MOD_ID + ".shingle.support.format.block.domum_ornamentum.shingle_flat_lower", "Supported by: %s");
        backingLangJson.put(Constants.MOD_ID + ".shingle.main.format.block.domum_ornamentum.shingle_flat_lower", "Main Material: %s");

        backingLangJson.put(Constants.MOD_ID + ".shingle.name.format.block.domum_ornamentum.shingle", "%s Shingles");
        backingLangJson.put(Constants.MOD_ID + ".shingle.support.format.block.domum_ornamentum.shingle", "Supported by: %s");
        backingLangJson.put(Constants.MOD_ID + ".shingle.main.format.block.domum_ornamentum.shingle", "Main Material: %s");

        backingLangJson.put(Constants.MOD_ID + ".shingle.name.format.block.domum_ornamentum.shingle_flat", "%s Flat Shingles");
        backingLangJson.put(Constants.MOD_ID + ".shingle.support.format.block.domum_ornamentum.shingle_flat", "Supported by: %s");
        backingLangJson.put(Constants.MOD_ID + ".shingle.main.format.block.domum_ornamentum.shingle_flat", "Main Material: %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingles Lang Provider";
    }
}
