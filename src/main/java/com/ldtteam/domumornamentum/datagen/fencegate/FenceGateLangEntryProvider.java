package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class FenceGateLangEntryProvider implements LanguageProvider.SubProvider {
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".fence-gate.name.format", "%s Fence gate");
    }
}
