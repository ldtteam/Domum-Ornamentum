package com.ldtteam.data;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class LanguageProvider implements DataProvider {
    private final DataGenerator generator;
    private final String modId;
    private final String language;
    private final List<SubProvider> subProviders;

    protected LanguageProvider(DataGenerator generator, String modId, String language, List<SubProvider> subProviders) {
        this.generator = generator;
        this.modId = modId;
        this.language = language;
        this.subProviders = subProviders;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<String, String> translations = new TreeMap<>();
        LanguageAcceptor acceptor = (key, value) -> {
            translations.put(key, value);

            // Since 26.2, BlockItem#getDescriptionId() is item.<mod>.<id>.
            // The legacy providers only emitted block.<mod>.<id>, which left
            // every generated Domum Ornamentum item showing its raw registry
            // id in creative tabs and tooltips. Keep the block translation as
            // the single source of truth and expose the corresponding item
            // key for concrete block ids.
            final String blockPrefix = "block." + modId + ".";
            if (key.startsWith(blockPrefix) && key.indexOf('.', blockPrefix.length()) < 0)
            {
                translations.put("item." + modId + "." + key.substring(blockPrefix.length()), value);
            }
        };
        subProviders.forEach(provider -> provider.addTranslations(acceptor));

        JsonObject root = new JsonObject();
        translations.forEach(root::addProperty);
        PackOutput output = generator.getPackOutput();
        Identifier id = Identifier.fromNamespaceAndPath(modId, language);
        return DataProvider.saveStable(cache, root, output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang").json(id));
    }

    @Override
    public String getName() {
        return "Languages: " + modId;
    }

    @FunctionalInterface
    public interface LanguageAcceptor {
        void add(String key, String value);
    }

    public interface SubProvider {
        void addTranslations(LanguageAcceptor acceptor);
    }
}
