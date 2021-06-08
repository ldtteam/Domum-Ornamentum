package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.TimberFrameBlock;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class TimberFramesLangEntryProvider implements IDataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public TimberFramesLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull DirectoryCache cache) throws IOException
    {
        for (final TimberFrameType type : TimberFrameType.values())
        {
            final String reference = Constants.MOD_ID + ".timber.frame.type." + type.getName();
            final String value = type.getLangName();

            backingLangJson.put(reference, value);
        }

        backingLangJson.put(Constants.MOD_ID + ".timber.frame.name.format", "Framed %s");
        backingLangJson.put(Constants.MOD_ID + ".timber.frame.header", "Framing:");
        backingLangJson.put(Constants.MOD_ID + ".timber.frame.type.format", "  - Type:     %s");
        backingLangJson.put(Constants.MOD_ID + ".timber.frame.block.format", "  - Material: %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Timber Frames Lang Provider";
    }
}
