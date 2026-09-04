package com.ldtteam.domumornamentum.datagen.global;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public abstract class DomumRecipeProvider extends RecipeProvider {
    protected DomumRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    protected abstract void buildRecipes();

    public static final class Runner<T extends DomumRecipeProvider> extends RecipeProvider.Runner {
        private final String name;
        private final BiFunction<HolderLookup.Provider, RecipeOutput, T> factory;

        public Runner(
            final PackOutput packOutput,
            final CompletableFuture<HolderLookup.Provider> registries,
            final String name,
            final BiFunction<HolderLookup.Provider, RecipeOutput, T> factory
        ) {
            super(packOutput, registries);
            this.name = name;
            this.factory = factory;
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return factory.apply(registries, output);
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
