package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.domumornamentum.datagen.LanguageProviderWrapper;
import com.ldtteam.domumornamentum.util.Constants;

public class FenceGateLangEntryProvider implements LanguageProviderWrapper.SubProvider {
    @Override
    public void addTranslations(LanguageProviderWrapper.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".fence-gate.name.format", "%s Fence gate");
    }
}
