package com.ldtteam.domumornamentum.datagen.trapdoor.stat;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class StaticTrapdoorsLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public StaticTrapdoorsLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".statictrapdoor.name.format", "%s Panel");
        backingLangJson.put(Constants.MOD_ID + ".statictrapdoor.type.format", "Variant: %s");
        backingLangJson.put(Constants.MOD_ID + ".statictrapdoor.block.format", "Material: %s");

        for (final TrapdoorType value : TrapdoorType.values())
        {
            backingLangJson.put(Constants.MOD_ID + ".statictrapdoor.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Static Trapdoors Lang Provider";
    }
}
