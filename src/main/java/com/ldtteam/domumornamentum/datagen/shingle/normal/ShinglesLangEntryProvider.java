package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ShinglesLangEntryProvider implements IDataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public ShinglesLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void act(@NotNull DirectoryCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".shingle.name.format", "%s Shingles");
        backingLangJson.put(Constants.MOD_ID + ".shingle.support.format", "Supported by: %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Shingles Lang Provider";
    }
}
