package com.ldtteam.domumornamentum.datagen.walls.paper;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PaperwallLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public PaperwallLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".paperwall.name.format", "%s framed thin wall");
        backingLangJson.put(Constants.MOD_ID + ".paperwall.header", "Materials:");
        backingLangJson.put(Constants.MOD_ID + ".paperwall.frame.format", "  - Frame:     %s");
        backingLangJson.put(Constants.MOD_ID + ".paperwall.center.format", "  - Center:    %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Paperwalls Lang Provider";
    }
}
