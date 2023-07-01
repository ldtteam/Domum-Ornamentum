package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ExtraLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public ExtraLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format", "%s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.black", "Black %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.blue", "Blue %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.brown", "Brown %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.cyan", "Cyan %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.gray", "Gray %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.green", "Green %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.light_blue", "Light Blue %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.light_gray", "Light Gray %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.lime", "Lime %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.magenta", "Magenta %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.orange", "Orange %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.pink", "Pink %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.purple", "Purple %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.red", "Red %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.white", "White %s Extra");
        backingLangJson.put(Constants.MOD_ID + ".extra.name.format.yellow", "Yellow %s Extra");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Lang Provider";
    }
}
