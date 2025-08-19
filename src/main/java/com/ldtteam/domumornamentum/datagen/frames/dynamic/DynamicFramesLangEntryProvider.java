package com.ldtteam.domumornamentum.datagen.frames.dynamic;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;

public class DynamicFramesLangEntryProvider implements LanguageProvider.SubProvider {

    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".dynamic.frame.name.format", "Dynamic Framed %s");
    }
}
