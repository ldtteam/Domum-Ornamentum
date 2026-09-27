package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

public class GlobalRecipeProvider extends RecipeProvider {

    public GlobalRecipeProvider(Provider lookupProvider, RecipeOutput recipeOutput) {
        super(lookupProvider, recipeOutput);
    }

    private void buildCutterRecipe() {
        final ShapedRecipeBuilder cutterRecipeBuilder = this.shaped(RecipeCategory.TOOLS, ModBlocks.getInstance().getArchitectsCutter().asItem(), 1);
        cutterRecipeBuilder.define('X', Items.IRON_INGOT);
        cutterRecipeBuilder.define('S', Items.STONE_SLAB);
        cutterRecipeBuilder.define('L', ItemTags.LOGS);
        cutterRecipeBuilder.pattern(" X ");
        cutterRecipeBuilder.pattern("SSS");
        cutterRecipeBuilder.pattern("LLL");
        cutterRecipeBuilder.unlockedBy("has_iron_ingot", this.has(Items.IRON_INGOT));
        cutterRecipeBuilder.unlockedBy("has_stone_slab", this.has(Items.STONE_SLAB));
        cutterRecipeBuilder.unlockedBy("has_log", this.has(ItemTags.LOGS));
        cutterRecipeBuilder.save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "architects_cutter")));
    }

    @Override
    protected void buildRecipes() {
        buildCutterRecipe();
        buildBarrelRecipe();
    }

    private void buildBarrelRecipe() {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.getInstance().getStandingBarrel())
                .define('S', Items.STICK)
                .define('W', ItemTags.PLANKS)
                .pattern("SWS")
                .pattern("SWS")
                .pattern("SWS")
                .unlockedBy("has_stick", this.has(Items.STICK))
                .unlockedBy("has_planks", this.has(ItemTags.PLANKS))
                .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, BuiltInRegistries.BLOCK.getKey(ModBlocks.getInstance().getStandingBarrel()).getPath())));

        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.getInstance().getLayingBarrel())
                .define('S', Items.STICK)
                .define('W', ItemTags.PLANKS)
                .pattern("SSS")
                .pattern("WWW")
                .pattern("SSS")
                .unlockedBy("has_stick", this.has(Items.STICK))
                .unlockedBy("has_planks", this.has(ItemTags.PLANKS))
                .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, BuiltInRegistries.BLOCK.getKey(ModBlocks.getInstance().getLayingBarrel()).getPath())));
    }

    public static void register(GatherDataEvent.Server event)
    {
        event.addProvider(new RecipeProvider.Runner(event.getGenerator().getPackOutput(), event.getLookupProvider()) {
            @Override
            protected @NonNull RecipeProvider createRecipeProvider(net.minecraft.core.HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
                return new GlobalRecipeProvider(registries, output);
            }
            @Override
            public @NonNull String getName() { return "GlobalRecipeProvider"; }
        });
    }

}
