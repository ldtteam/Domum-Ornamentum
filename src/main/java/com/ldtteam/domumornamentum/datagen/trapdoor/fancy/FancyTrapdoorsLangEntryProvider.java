package com.ldtteam.domumornamentum.datagen.trapdoor.fancy;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FancyTrapdoorsLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public FancyTrapdoorsLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".trapdoor.fancy.name.format", "%s Trapdoor");
        backingLangJson.put(Constants.MOD_ID + ".trapdoor.fancy.type.format", "Variant: %s");

        for (final FancyTrapdoorType value : FancyTrapdoorType.values())
        {
            backingLangJson.put(Constants.MOD_ID + ".trapdoor.fancy.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "FancyTrapdoors Lang Provider";
    }
}
