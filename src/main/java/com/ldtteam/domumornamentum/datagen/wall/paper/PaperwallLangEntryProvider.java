package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class PaperwallLangEntryProvider implements LanguageProvider.SubProvider {
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".paperwall.name.format", "%s framed thin wall");
        acceptor.add(Constants.MOD_ID + ".paperwall.header", "Materials:");
        acceptor.add(Constants.MOD_ID + ".paperwall.frame.format", "  - Frame:     %s");
        acceptor.add(Constants.MOD_ID + ".paperwall.center.format", "  - Center:    %s");
    }
}
