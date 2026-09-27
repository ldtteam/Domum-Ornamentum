package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.Objects;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

public class MateriallyTexturedBlockRecipeProvider extends RecipeProvider
{

    public MateriallyTexturedBlockRecipeProvider(Provider lookupProvider, RecipeOutput recipeOutput) {
        super(lookupProvider, recipeOutput);
    }

    @Override
    protected void buildRecipes()
    {
        BuiltInRegistries.BLOCK.forEach(
                block -> {
                    if (Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getNamespace().equals(Constants.MOD_ID) && block instanceof IMateriallyTexturedBlock materiallyTexturedBlock) {
                        materiallyTexturedBlock.buildRecipes(this.output);
                    }
                }
        );
    }

    public static void register(GatherDataEvent.Server event)
    {
        event.addProvider(new RecipeProvider.Runner(event.getGenerator().getPackOutput(), event.getLookupProvider()) {
            @Override
            protected @NonNull RecipeProvider createRecipeProvider(net.minecraft.core.HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
                return new MateriallyTexturedBlockRecipeProvider(registries, output);
            }
            @Override
            public @NonNull String getName() { return "MateriallyTexturedBlockRecipeProvider"; }
        });
    }

}
