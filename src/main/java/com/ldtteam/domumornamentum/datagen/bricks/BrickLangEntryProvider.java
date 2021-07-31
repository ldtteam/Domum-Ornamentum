package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.datagenerators.lang.LangJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.block.types.BrickType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BrickLangEntryProvider implements DataProvider
{
    private final DataGenerator dataGenerator;
    private final LangJson backingLangJson;

    public BrickLangEntryProvider(final DataGenerator dataGenerator, LangJson backingLangJson)
    {
        this.dataGenerator = dataGenerator;
        this.backingLangJson = backingLangJson;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        backingLangJson.put(Constants.MOD_ID + ".brick.name." + BrickType.BEIGE + BrickType.SUFFIX, "Beige Brick");
        backingLangJson.put(Constants.MOD_ID + ".brick.name." + BrickType.BROWN + BrickType.SUFFIX, "Brown Brick");
        backingLangJson.put(Constants.MOD_ID + ".brick.name." + BrickType.CREAM + BrickType.SUFFIX, "Cream Brick");

        backingLangJson.put(Constants.MOD_ID + ".brick.name." + BrickType.BEIGE_STONE + BrickType.SUFFIX, "Beige Stone Brick");
        backingLangJson.put(Constants.MOD_ID + ".brick.name." + BrickType.BROWN_STONE + BrickType.SUFFIX, "Brown Stone Brick");
        backingLangJson.put(Constants.MOD_ID + ".brick.name." + BrickType.CREAM_STONE + BrickType.SUFFIX, "Cream Stone Brick");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Lang Provider";
    }
}
