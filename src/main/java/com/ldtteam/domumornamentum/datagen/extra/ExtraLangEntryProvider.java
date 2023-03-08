package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.util.Constants;

public class ExtraLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".extra.name.format", "%s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.black", "Black %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.blue", "Blue %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.brown", "Brown %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.cyan", "Cyan %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.gray", "Gray %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.green", "Green %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.light_blue", "Light Blue %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.light_gray", "Light Gray %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.lime", "Lime %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.magenta", "Magenta %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.orange", "Orange %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.pink", "Pink %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.purple", "Purple %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.red", "Red %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.white", "White %s Extra");
        acceptor.add(Constants.MOD_ID + ".extra.name.format.yellow", "Yellow %s Extra");
    }
}
