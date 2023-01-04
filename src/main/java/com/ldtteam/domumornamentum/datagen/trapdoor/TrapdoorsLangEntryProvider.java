package com.ldtteam.domumornamentum.datagen.trapdoor;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.util.Constants;

public class TrapdoorsLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".trapdoor.name.format", "%s Trapdoor");
        acceptor.add(Constants.MOD_ID + ".trapdoor.type.format", "Variant: %s");
        acceptor.add(Constants.MOD_ID + ".trapdoor.block.format", "Material: %s");

        for (final TrapdoorType value : TrapdoorType.values())
        {
            acceptor.add(Constants.MOD_ID + ".trapdoor.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }
}
