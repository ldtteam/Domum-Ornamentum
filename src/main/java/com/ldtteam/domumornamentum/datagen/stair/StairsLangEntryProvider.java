package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class StairsLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public StairsLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".stair.name.format", "%s Stairs");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Stairs Lang Provider";
    }
}
