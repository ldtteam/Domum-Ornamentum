package com.ldtteam.domumornamentum.datagen.frames.light;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FramedLightLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public FramedLightLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        for (final TimberFrameType type : TimberFrameType.values())
        {
            final String reference = Constants.MOD_ID + ".light.frame.type." + type.getName();
            final String value = type.getLangName();

            backingLangJson.put(reference, value);
        }

        backingLangJson.put(Constants.MOD_ID + ".light.frame.name.format", "Framed %s");
        backingLangJson.put(Constants.MOD_ID + ".light.frame.header", "Framing:");
        backingLangJson.put(Constants.MOD_ID + ".light.frame.type.format", "  - Type:     %s");
        backingLangJson.put(Constants.MOD_ID + ".light.frame.block.format", "  - Material: %s");
        backingLangJson.put(Constants.MOD_ID + ".light.center.header", "Center:");
        backingLangJson.put(Constants.MOD_ID + ".light.center.block.format", "  - Material: %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Framed Light Lang Provider";
    }
}
