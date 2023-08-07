package com.ldtteam.domumornamentum.datagen.shingle.normal;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class ShinglesLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".shingle.name.format.block.domum_ornamentum.shingle_flat_lower", "%s Flat Lower Shingles");
        acceptor.add(Constants.MOD_ID + ".shingle.support.format", "Supported by: %s");
        acceptor.add(Constants.MOD_ID + ".shingle.main.format", "Main Material: %s");

        acceptor.add(Constants.MOD_ID + ".shingle.name.format.block.domum_ornamentum.shingle", "%s Shingles");
        acceptor.add(Constants.MOD_ID + ".shingle.name.format.block.domum_ornamentum.shingle_flat", "%s Flat Shingles");
    }
}
