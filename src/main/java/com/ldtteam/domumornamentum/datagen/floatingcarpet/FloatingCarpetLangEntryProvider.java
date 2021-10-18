package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FloatingCarpetLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public FloatingCarpetLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        for (final FloatingCarpetBlock floatingCarpet : ModBlocks.getInstance().getFloatingCarpets())
        {
            backingLangJson.put(floatingCarpet.getDescriptionId(), floatingCarpet.getColor().getName().substring(0, 1).toUpperCase() +
                                 floatingCarpet.getColor().getName().substring(1) + " Floating carpet");
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Floating Carpet Lang Provider";
    }
}
