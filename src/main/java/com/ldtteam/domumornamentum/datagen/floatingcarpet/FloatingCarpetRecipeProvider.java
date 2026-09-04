package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import com.ldtteam.domumornamentum.datagen.global.DomumRecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class FloatingCarpetRecipeProvider extends DomumRecipeProvider {

    public FloatingCarpetRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        for (final FloatingCarpetBlock block : ModBlocks.getInstance().getFloatingCarpets()) {
            final DyeColor color = block.getColor();
            final Block wool = Blocks.WOOL.pick(color);
            shapeless(RecipeCategory.DECORATIONS, block, 3)
                    .requires(wool, 2)
                    .requires(Tags.Items.STRINGS)
                    .group("floating_carpets")
                    .unlockedBy("has_string", has(Tags.Items.STRINGS))
                    .unlockedBy("has_wool", has(wool))
                    .save(this.output);
        }
    }

}
