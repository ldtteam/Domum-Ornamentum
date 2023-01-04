package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;

public class FloatingCarpetLangEntryProvider implements LanguageProvider.SubProvider {

    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        for (final FloatingCarpetBlock floatingCarpet : ModBlocks.getInstance().getFloatingCarpets())
        {
            acceptor.add(floatingCarpet.getDescriptionId(), floatingCarpet.getColor().getName().substring(0, 1).toUpperCase() +
                    floatingCarpet.getColor().getName().substring(1) + " Floating carpet");
        }
    }
}
