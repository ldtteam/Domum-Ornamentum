package com.ldtteam.domumornamentum.datagen.frames.dynamic;

import com.ldtteam.domumornamentum.datagen.LanguageProviderWrapper;
import com.ldtteam.domumornamentum.util.Constants;

public class DynamicFramesLangEntryProvider implements LanguageProviderWrapper.SubProvider {

    @Override
    public void addTranslations(LanguageProviderWrapper.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".dynamic.frame.name.format", "Dynamic Framed %s");
    }
}
