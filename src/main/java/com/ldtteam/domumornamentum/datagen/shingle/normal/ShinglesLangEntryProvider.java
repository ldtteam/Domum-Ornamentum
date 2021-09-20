package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
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
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".shingle.name.format", "%s Shingles");
        backingLangJson.put(Constants.MOD_ID + ".shingle.support.format", "Supported by: %s");
        backingLangJson.put(Constants.MOD_ID + ".shingle.main.format", "Main Material: %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingles Lang Provider";
    }
}
