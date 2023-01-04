package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.types.BrickType;
import com.ldtteam.domumornamentum.util.Constants;

public class BrickLangEntryProvider implements LanguageProvider.SubProvider
{

    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add("block." + Constants.MOD_ID + "." + BrickType.BEIGE.getSerializedName(), "Beige Bricks");
        acceptor.add("block." + Constants.MOD_ID + "." + BrickType.BROWN.getSerializedName(), "Brown Bricks");
        acceptor.add("block." + Constants.MOD_ID + "." + BrickType.CREAM.getSerializedName(), "Cream Bricks");
        acceptor.add("block." + Constants.MOD_ID + "." + BrickType.SAND.getSerializedName(), "Sand Bricks");

        acceptor.add("block." + Constants.MOD_ID + "." + BrickType.BEIGE_STONE.getSerializedName(), "Beige Stone Bricks");
        acceptor.add("block." + Constants.MOD_ID + "." + BrickType.BROWN_STONE.getSerializedName(), "Brown Stone Bricks");
        acceptor.add("block." + Constants.MOD_ID + "." + BrickType.CREAM_STONE.getSerializedName(), "Cream Stone Bricks");
        acceptor.add("block." + Constants.MOD_ID + "." + BrickType.SAND_STONE.getSerializedName(), "Sand Stone Bricks");
    }
}
