package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class StairsLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".stair.name.format", "%s Stairs");
    }
}
