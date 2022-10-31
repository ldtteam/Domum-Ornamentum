package com.ldtteam.domumornamentum.datagen.pillar;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.ModCreativeTabs;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.item.decoration.PillarBlockItem;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PillarLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public PillarLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull CachedOutput cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".blockpillar.name.format", "Round %s Pillar");
        backingLangJson.put(Constants.MOD_ID + ".blockypillar.name.format", "Voxel %s Pillar");
        backingLangJson.put(Constants.MOD_ID + ".squarepillar.name.format", "Square %s Pillar");

        backingLangJson.put(Constants.MOD_ID + ".squarepillar.column.format", "Main Material: %s");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Pillar Lang Provider";
    }
}
