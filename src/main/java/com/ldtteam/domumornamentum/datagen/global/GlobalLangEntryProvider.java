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
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".timber_frames", "DO - Framed Blocks");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".shingles", "DO - Shingles");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".general", "Domum Ornamentum (DO)");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".paperwalls", "DO - Thin framed walls");
        backingLangJson.put("itemGroup." + Constants.MOD_ID + ".shingle_slabs", "DO - Shingle slabs");
        backingLangJson.put("block." + Constants.MOD_ID + ".architectscutter", "Architects cutter");


        IDataProvider.save(DataGeneratorConstants.GSONLang, cache, backingLangJson.serialize(), dataGenerator.getOutputFolder().resolve(DataGeneratorConstants.EN_US_LANG));
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Global Lang Provider";
    }
}
