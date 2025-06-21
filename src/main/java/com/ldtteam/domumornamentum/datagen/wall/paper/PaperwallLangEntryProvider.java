package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class PaperwallLangEntryProvider implements LanguageProvider.SubProvider {
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".blockpaperwall.name.format", "%s Framed Pane");
        acceptor.add(Constants.MOD_ID + ".blockpaperwall.header", "Materials:");
        acceptor.add(Constants.MOD_ID + ".blockpaperwall.frame.format", "  - Frame:     %s");
        acceptor.add(Constants.MOD_ID + ".blockpaperwall.center.format", "  - Center:    %s");

        acceptor.add(Constants.MOD_ID + ".blocktiledpaperwall.name.format", "%s Tiled Pane");
        acceptor.add(Constants.MOD_ID + ".blocktiledpaperwall.header", "Materials:");
        acceptor.add(Constants.MOD_ID + ".blocktiledpaperwall.frame.format", "  - Frame:     %s");
        acceptor.add(Constants.MOD_ID + ".blocktiledpaperwall.center.format", "  - Center:    %s");
    }
}
