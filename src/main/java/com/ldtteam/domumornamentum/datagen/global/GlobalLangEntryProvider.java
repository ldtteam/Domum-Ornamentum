package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GlobalLangEntryProvider implements IDataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public GlobalLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull DirectoryCache cache) throws IOException
    {
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".timber_frames", "Framed Blocks");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".shingles", "Shingles");

        IDataProvider.save(DataGeneratorConstants.GSONLang, cache, backingLangJson.serialize(), dataGenerator.getOutputFolder().resolve(DataGeneratorConstants.EN_US_LANG));
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Global Lang Provider";
    }
}
