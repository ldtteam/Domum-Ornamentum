package com.ldtteam.domumornamentum.datagen.door.fancy;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.util.Constants;

public class FancyDoorsLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".fancydoor.name.format", "Fancy %s Door");
        acceptor.add(Constants.MOD_ID + ".fancydoor.type.format", "Variant: %s");

        acceptor.add(Constants.MOD_ID + ".fancydoor.frame.header", "Frame:");
        acceptor.add(Constants.MOD_ID + ".fancydoor.center.header", "Center:");
        acceptor.add(Constants.MOD_ID + ".fancydoor.center.block.format", "  - Material: %s");
        acceptor.add(Constants.MOD_ID + ".fancydoor.frame.block.format", "  - Material: %s");

        for (final FancyDoorType value : FancyDoorType.values())
        {
            acceptor.add(Constants.MOD_ID + ".fancydoor.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }
}
