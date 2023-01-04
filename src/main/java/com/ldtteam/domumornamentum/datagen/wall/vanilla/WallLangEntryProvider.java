package com.ldtteam.domumornamentum.datagen.wall.vanilla;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class WallLangEntryProvider implements LanguageProvider.SubProvider
{

    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".wall.name.format", "%s Wall");
    }
}
