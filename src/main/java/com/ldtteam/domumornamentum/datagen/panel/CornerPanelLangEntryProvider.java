package com.ldtteam.domumornamentum.datagen.panel;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class CornerPanelLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".corner.panel.name.format", "%s Corner Panel");
        acceptor.add(Constants.MOD_ID + ".corner.panel.type.format", "Variant: %s");
        acceptor.add(Constants.MOD_ID + ".corner.panel.block.format", "Material: %s");
    }
}
