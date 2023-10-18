package com.ldtteam.domumornamentum.datagen.allbrick;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AllBrickLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final     LangJson      backingLangJson;

    public AllBrickLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".dark_brick.name.format", "Dark %s Brick");
        backingLangJson.put(Constants.MOD_ID + ".light_brick.name.format", "Light %s Brick");

        backingLangJson.put(Constants.MOD_ID + ".allbrick.column.format", "Main Material: %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "AllBrick Lang Provider";
    }
}
