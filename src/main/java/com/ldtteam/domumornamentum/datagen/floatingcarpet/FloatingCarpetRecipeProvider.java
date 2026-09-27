package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.HashMap;
import java.util.Map;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.NonNull;

public class FloatingCarpetRecipeProvider extends RecipeProvider {

    public FloatingCarpetRecipeProvider(Provider lookupProvider, RecipeOutput recipeOutput) {
        super(lookupProvider, recipeOutput);
    }

    @Override
    protected void buildRecipes() {
        final Map<DyeColor, Block> wools = new HashMap<>();
        wools.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        wools.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        wools.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        wools.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
        wools.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        wools.put(DyeColor.RED, Blocks.RED_WOOL);
        wools.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        wools.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        wools.put(DyeColor.LIME, Blocks.LIME_WOOL);
        wools.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        wools.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        wools.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        wools.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        wools.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        wools.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        wools.put(DyeColor.PINK, Blocks.PINK_WOOL);

        for (final FloatingCarpetBlock block : ModBlocks.getInstance().getFloatingCarpets()) {
            final DyeColor color = block.getColor();
            final ShapelessRecipeBuilder builder = this.shapeless(RecipeCategory.DECORATIONS, block, 3);
            builder.requires(wools.get(color), 2);
            builder.requires(Tags.Items.STRINGS);
            builder.group("floating_carpets");
            builder.unlockedBy("has_string", this.has(Tags.Items.STRINGS));
            builder.unlockedBy("has_wool", this.has(wools.get(color)));
            builder.save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, BuiltInRegistries.BLOCK.getKey(block).getPath())));
        }
    }

    public static void register(GatherDataEvent.Server event)
    {
        event.addProvider(new RecipeProvider.Runner(event.getGenerator().getPackOutput(), event.getLookupProvider()) {
            @Override
            protected @NonNull RecipeProvider createRecipeProvider(net.minecraft.core.HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
                return new FloatingCarpetRecipeProvider(registries, output);
            }
            @Override
            public @NonNull String getName() { return "FloatingCarpetRecipeProvider"; }
        });
    }

}
