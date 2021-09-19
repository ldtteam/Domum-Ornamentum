package com.ldtteam.domumornamentum.datagen.shingle.slab;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ShingleSlabLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public ShingleSlabLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".shingle_slab.name.format", "%s Shingles");
        backingLangJson.put(Constants.MOD_ID + ".shingle_slab.support.format", "Supported by: %s");
        backingLangJson.put(Constants.MOD_ID + ".shingle_slab.cover.format", "Covered by: %s");
        backingLangJson.put(Constants.MOD_ID + ".shingle_slab.main.format", "Main Material: %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingle Slabs Lang Provider";
    }
}
