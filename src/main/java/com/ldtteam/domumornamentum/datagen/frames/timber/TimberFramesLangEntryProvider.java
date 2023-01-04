package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.util.Constants;

public class TimberFramesLangEntryProvider implements LanguageProvider.SubProvider {

    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        for (final TimberFrameType type : TimberFrameType.values())
        {
            final String reference = Constants.MOD_ID + ".timber.frame.type." + type.getName();
            final String value = type.getLangName();

            acceptor.add(reference, value);
        }

        acceptor.add(Constants.MOD_ID + ".timber.frame.name.format", "Framed %s");
        acceptor.add(Constants.MOD_ID + ".timber.frame.header", "Framing:");
        acceptor.add(Constants.MOD_ID + ".timber.frame.type.format", "  - Type:     %s");
        acceptor.add(Constants.MOD_ID + ".timber.frame.block.format", "  - Material: %s");
        acceptor.add(Constants.MOD_ID + ".timber.center.header", "Center:");
        acceptor.add(Constants.MOD_ID + ".timber.center.block.format", "  - Material: %s");
    }
}
