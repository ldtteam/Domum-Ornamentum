package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;

public class SlabLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) 
    {
        acceptor.add(ModBlocks.getInstance().getStackedSlab().getDescriptionId(), "Stacked Slab");
        acceptor.add(Constants.MOD_ID + ".slab.name.format", "%s Slab");
        acceptor.add(Constants.MOD_ID + ".stacked_slab.name.format", "%s / %s Stacked Slab");
    }
}
