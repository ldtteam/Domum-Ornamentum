package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SlabLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public SlabLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".slab.name.format", "%s Slab");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Slabs Lang Provider";
    }
}
