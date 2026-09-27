package com.ldtteam.domumornamentum.datagen.shingle.slab;

import com.ldtteam.domumornamentum.datagen.LanguageProviderWrapper;
import com.ldtteam.domumornamentum.util.Constants;

public class ShingleSlabLangEntryProvider implements LanguageProviderWrapper.SubProvider
{
    @Override
    public void addTranslations(LanguageProviderWrapper.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".shingle_slab.name.format", "%s Shingles");
        acceptor.add(Constants.MOD_ID + ".shingle_slab.support.format", "Supported by: %s");
        acceptor.add(Constants.MOD_ID + ".shingle_slab.cover.format", "Covered by: %s");
        acceptor.add(Constants.MOD_ID + ".shingle_slab.main.format", "Main Material: %s");
    }
}
