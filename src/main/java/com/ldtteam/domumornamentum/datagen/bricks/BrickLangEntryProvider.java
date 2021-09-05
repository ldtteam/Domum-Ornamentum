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
        backingLangJson.put("block." + Constants.MOD_ID + "." + BrickType.BEIGE.getSerializedName(), "Beige Bricks");
        backingLangJson.put("block." + Constants.MOD_ID + "." + BrickType.BROWN.getSerializedName(), "Brown Bricks");
        backingLangJson.put("block." + Constants.MOD_ID + "." + BrickType.CREAM.getSerializedName(), "Cream Bricks");

        backingLangJson.put("block." + Constants.MOD_ID + "." + BrickType.BEIGE_STONE.getSerializedName(), "Beige Stone Bricks");
        backingLangJson.put("block." + Constants.MOD_ID + "." + BrickType.BROWN_STONE.getSerializedName(), "Brown Stone Bricks");
        backingLangJson.put("block." + Constants.MOD_ID + "." + BrickType.CREAM_STONE.getSerializedName(), "Cream Stone Bricks");
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Lang Provider";
    }
}
