package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GlobalRecipeProvider extends RecipeProvider {

    public GlobalRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    private static void buildCutterRecipe(Consumer<FinishedRecipe> writer) {
        final ShapedRecipeBuilder cutterRecipeBuilder = new ShapedRecipeBuilder(RecipeCategory.TOOLS, ModBlocks.getInstance().getArchitectsCutter().asItem(), 1);
        cutterRecipeBuilder.define('X', Items.IRON_INGOT);
        cutterRecipeBuilder.define('S', Items.STONE_SLAB);
        cutterRecipeBuilder.define('L', ItemTags.LOGS);
        cutterRecipeBuilder.pattern(" X ");
        cutterRecipeBuilder.pattern("SSS");
        cutterRecipeBuilder.pattern("LLL");
        cutterRecipeBuilder.unlockedBy("has_iron_ingot", has(Items.IRON_INGOT));
        cutterRecipeBuilder.unlockedBy("has_stone_slab", has(Items.STONE_SLAB));
        cutterRecipeBuilder.unlockedBy("has_log", has(ItemTags.LOGS));
        cutterRecipeBuilder.save(writer);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        buildCutterRecipe(writer);
        buildBarrelRecipe(writer);
    }

    private void buildBarrelRecipe(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.getInstance().getStandingBarrel())
                .define('S', Items.STICK)
                .define('W', ItemTags.PLANKS)
                .pattern("SWS")
                .pattern("SWS")
                .pattern("SWS")
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.getInstance().getLayingBarrel())
                .define('S', Items.STICK)
                .define('W', ItemTags.PLANKS)
                .pattern("SSS")
                .pattern("WWW")
                .pattern("SSS")
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(writer);
    }

    @NotNull
    @Override
    public String getName() {
        return "Global Blocks Recipe Provider";
    }
}
