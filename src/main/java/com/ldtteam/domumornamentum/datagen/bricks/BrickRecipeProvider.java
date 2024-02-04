package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class BrickRecipeProvider extends RecipeProvider {

    public BrickRecipeProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeWriter) {
        ModBlocks.getInstance().getBricks().forEach(brickBlock -> brickBlockRecipe(recipeWriter, brickBlock));
    }

    private void brickBlockRecipe(RecipeOutput recipeWriter, BrickBlock brickBlock) {


        final ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, brickBlock, 4);
        builder.requires(brickBlock.getType().getIngredient(), 2);
        builder.requires(brickBlock.getType().getIngredient2(), 2);
        builder.unlockedBy("has_item1_" + Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(brickBlock.asItem().asItem())).toString().replace(":", "_"), has(brickBlock.getType().getIngredient()));
        builder.unlockedBy("has_item2_" + Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(brickBlock.asItem().asItem())).toString().replace(":", "_"), has(brickBlock.getType().getIngredient()));
        builder.save(recipeWriter);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Brick Blocks Recipe Provider";
    }
}
