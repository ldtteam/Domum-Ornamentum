package com.ldtteam.domumornamentum.datagen.allbrick;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class AllBrickLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".allbrick.name.format", "Round %s Pillar");
        acceptor.add(Constants.MOD_ID + ".allbrick.column.format", "Main Material: %s");
    }
}
