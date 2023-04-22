package com.ldtteam.domumornamentum.datagen.panel;

import com.ldtteam.data.LanguageProvider;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;

public class PostLangEntryProvider implements LanguageProvider.SubProvider
{
    @Override
    public void addTranslations(LanguageProvider.LanguageAcceptor acceptor) {
        acceptor.add(Constants.MOD_ID + ".post.name.format", "%s Post");
        acceptor.add(Constants.MOD_ID + ".post.type.format", "Variant: %s");
        acceptor.add(Constants.MOD_ID + ".post.block.format", "Material: %s");

        for (final PostType value : PostType.values())
        {
            acceptor.add(Constants.MOD_ID + ".post.type.name." + value.getTranslationKeySuffix(), value.getDefaultEnglishTranslation());
        }
    }
}
