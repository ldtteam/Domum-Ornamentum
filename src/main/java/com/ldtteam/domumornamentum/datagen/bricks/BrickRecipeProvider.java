package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

public class BrickRecipeProvider extends RecipeProvider {

    public BrickRecipeProvider(Provider lookupProvider, RecipeOutput recipeOutput) {
        super(lookupProvider, recipeOutput);
    }

    @Override
    protected void buildRecipes() {
        ModBlocks.getInstance().getBricks().forEach(brickBlock -> brickBlockRecipe(brickBlock));
    }

    private void brickBlockRecipe(BrickBlock brickBlock) {
        final ShapelessRecipeBuilder builder = this.shapeless(RecipeCategory.TOOLS, brickBlock, 4);
        builder.requires(brickBlock.getType().getIngredient(), 2);
        builder.requires(brickBlock.getType().getIngredient2(), 2);
        final String itemName = BuiltInRegistries.ITEM.getKey(brickBlock.asItem().asItem()).getPath();
        builder.unlockedBy("has_item1_" + itemName, this.has(brickBlock.getType().getIngredient()));
        builder.unlockedBy("has_item2_" + itemName, this.has(brickBlock.getType().getIngredient2()));
        builder.save(this.output, ResourceKey.create(net.minecraft.core.registries.Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "brick_" + itemName)));
    }
    public static void register(GatherDataEvent.Server event)
    {
        event.addProvider(new RecipeProvider.Runner(event.getGenerator().getPackOutput(), event.getLookupProvider()) {
            @Override
            protected @NonNull RecipeProvider createRecipeProvider(net.minecraft.core.HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
                return new BrickRecipeProvider(registries, output);
            }
            @Override
            public @NonNull String getName() { return "BrickRecipeProvider"; }
        });
    }

}
