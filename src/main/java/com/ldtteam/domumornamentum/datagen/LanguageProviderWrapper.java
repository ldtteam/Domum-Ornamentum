package com.ldtteam.domumornamentum.datagen;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/**
 * Wrapper around NeoForge's LanguageProvider with support for sub-providers.
 * Replaces the old com.ldtteam.data.LanguageProvider.
 */
public class LanguageProviderWrapper extends LanguageProvider {
    private final List<SubProvider> subProviders;

    public LanguageProviderWrapper(PackOutput output, String modId, String locale, List<SubProvider> subProviders) {
        super(output, modId, locale);
        this.subProviders = subProviders;
    }

    @Override
    protected void addTranslations() {
        for (SubProvider subProvider : subProviders) {
            subProvider.addTranslations(LanguageProviderWrapper.this::add);
        }
    }

    /**
     * A sub-provider interface for adding translations.
     */
    public interface SubProvider {
        void addTranslations(LanguageAcceptor acceptor);
    }

    /**
     * Interface for accepting language entries.
     */
    public interface LanguageAcceptor {
        void add(String key, String value);
    }
}
