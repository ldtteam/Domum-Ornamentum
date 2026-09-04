package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import com.ldtteam.domumornamentum.datagen.global.DomumRecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BrickRecipeProvider extends DomumRecipeProvider {

    public BrickRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ModBlocks.getInstance().getBricks().forEach(brickBlock -> brickBlockRecipe(this.output, brickBlock));
    }

    private void brickBlockRecipe(RecipeOutput recipeWriter, BrickBlock brickBlock) {


        final ShapelessRecipeBuilder builder = shapeless(RecipeCategory.TOOLS, brickBlock, 4);
        builder.requires(brickBlock.getType().getIngredient(), 2);
        builder.requires(brickBlock.getType().getIngredient2(), 2);
        builder.unlockedBy("has_item1_" + Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(brickBlock.asItem().asItem())).toString().replace(":", "_"), has(brickBlock.getType().getIngredient()));
        builder.unlockedBy("has_item2_" + Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(brickBlock.asItem().asItem())).toString().replace(":", "_"), has(brickBlock.getType().getIngredient()));
        builder.save(recipeWriter);
    }

}
