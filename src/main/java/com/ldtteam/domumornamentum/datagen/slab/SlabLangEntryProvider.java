package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.datagen.LanguageProviderWrapper;
import com.ldtteam.domumornamentum.util.Constants;

public class SlabLangEntryProvider implements LanguageProviderWrapper.SubProvider
{
    @Override
    public void addTranslations(LanguageProviderWrapper.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".slab.name.format", "%s Slab");
    }
}
