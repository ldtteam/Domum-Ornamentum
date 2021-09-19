package com.ldtteam.domumornamentum.datagen.door.fancy;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.DoorType;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FancyDoorsLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public FancyDoorsLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".fancydoor.name.format", "Fancy %s Door");
        backingLangJson.put(Constants.MOD_ID + ".fancydoor.type.format", "Variant: %s");

        backingLangJson.put(Constants.MOD_ID + ".fancydoor.frame.header", "Frame:");
        backingLangJson.put(Constants.MOD_ID + ".fancydoor.center.header", "Center:");
        backingLangJson.put(Constants.MOD_ID + ".fancydoor.center.block.format", "  - Material: %s");
        backingLangJson.put(Constants.MOD_ID + ".fancydoor.frame.block.format", "  - Material: %s");

        for (final FancyDoorType value : FancyDoorType.values())
        {
            backingLangJson.put(Constants.MOD_ID + ".fancydoor.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Fancy Doors Lang Provider";
    }
}
