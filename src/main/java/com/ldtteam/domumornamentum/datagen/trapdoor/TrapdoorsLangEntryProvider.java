package com.ldtteam.domumornamentum.datagen.trapdoor;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class TrapdoorsLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public TrapdoorsLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".trapdoor.name.format", "%s Trapdoor");
        backingLangJson.put(Constants.MOD_ID + ".trapdoor.type.format", "Variant: %s");
        backingLangJson.put(Constants.MOD_ID + ".trapdoor.block.format", "Material: %s");

        for (final TrapdoorType value : TrapdoorType.values())
        {
            backingLangJson.put(Constants.MOD_ID + ".trapdoor.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Trapdoors Lang Provider";
    }
}
