package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import com.ldtteam.domumornamentum.datagen.global.DomumRecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;


public class GlobalRecipeProvider extends DomumRecipeProvider {

    public GlobalRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
    }

    private void buildCutterRecipe(RecipeOutput writer) {
        final ShapedRecipeBuilder cutterRecipeBuilder = shaped(RecipeCategory.TOOLS, ModBlocks.getInstance().getArchitectsCutter().asItem(), 1);
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
    protected void buildRecipes() {
        buildCutterRecipe(this.output);
        buildBarrelRecipe(this.output);
    }

    private void buildBarrelRecipe(RecipeOutput writer) {
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.getInstance().getStandingBarrel())
                .define('S', Items.STICK)
                .define('W', ItemTags.PLANKS)
                .pattern("SWS")
                .pattern("SWS")
                .pattern("SWS")
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(writer);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.getInstance().getLayingBarrel())
                .define('S', Items.STICK)
                .define('W', ItemTags.PLANKS)
                .pattern("SSS")
                .pattern("WWW")
                .pattern("SSS")
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(writer);
    }
}
