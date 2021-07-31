package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FenceGateLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public FenceGateLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".fence-gate.name.format", "%s FenceGate");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "FenceGates Lang Provider";
    }
}
