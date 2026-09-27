package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.domumornamentum.datagen.LanguageProviderWrapper;
import com.ldtteam.domumornamentum.util.Constants;

public class StairsLangEntryProvider implements LanguageProviderWrapper.SubProvider
{
    @Override
    public void addTranslations(LanguageProviderWrapper.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".stair.name.format", "%s Stairs");
    }
}
