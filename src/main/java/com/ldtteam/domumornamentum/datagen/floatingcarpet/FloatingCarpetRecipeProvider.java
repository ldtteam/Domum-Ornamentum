package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FloatingCarpetRecipeProvider extends RecipeProvider {

    public FloatingCarpetRecipeProvider(final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(final @NotNull Consumer<FinishedRecipe> builder) {
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
            ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, block, 3)
                    .requires(wools.get(color), 2)
                    .requires(Tags.Items.STRING)
                    .group("floating_carpets")
                    .unlockedBy("has_string", has(Tags.Items.STRING))
                    .unlockedBy("has_wool", has(wools.get(color)))
                    .save(builder);
        }
    }

    @NotNull
    @Override
    public String getName() {
        return "Floating Carpet Recipe Provider";
    }
}
