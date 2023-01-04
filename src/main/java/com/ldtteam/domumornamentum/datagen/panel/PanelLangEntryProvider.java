package com.ldtteam.domumornamentum.datagen.panel;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.util.Constants;

public class PanelLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".panel.name.format", "%s Panel");
        acceptor.add(Constants.MOD_ID + ".panel.type.format", "Variant: %s");
        acceptor.add(Constants.MOD_ID + ".panel.block.format", "Material: %s");

        for (final TrapdoorType value : TrapdoorType.values())
        {
            acceptor.add(Constants.MOD_ID + ".panel.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }
}
