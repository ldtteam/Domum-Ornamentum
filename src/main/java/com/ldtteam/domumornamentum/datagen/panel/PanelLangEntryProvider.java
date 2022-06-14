package com.ldtteam.domumornamentum.datagen.panel;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PanelLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public PanelLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".panel.name.format", "%s Panel");
        backingLangJson.put(Constants.MOD_ID + ".panel.type.format", "Variant: %s");
        backingLangJson.put(Constants.MOD_ID + ".panel.block.format", "Material: %s");

        for (final TrapdoorType value : TrapdoorType.values())
        {
            backingLangJson.put(Constants.MOD_ID + ".panel.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Panel Lang Provider";
    }
}
