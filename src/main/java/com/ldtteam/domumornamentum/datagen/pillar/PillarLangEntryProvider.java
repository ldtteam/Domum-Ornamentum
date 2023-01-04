package com.ldtteam.domumornamentum.datagen.pillar;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class PillarLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".blockpillar.name.format", "Round %s Pillar");
        acceptor.add(Constants.MOD_ID + ".blockypillar.name.format", "Voxel %s Pillar");
        acceptor.add(Constants.MOD_ID + ".squarepillar.name.format", "Square %s Pillar");

        acceptor.add(Constants.MOD_ID + ".pillar.header", "Type:");
        acceptor.add(Constants.MOD_ID + ".pillar.column.format", "Main Material: %s");
    }
}
