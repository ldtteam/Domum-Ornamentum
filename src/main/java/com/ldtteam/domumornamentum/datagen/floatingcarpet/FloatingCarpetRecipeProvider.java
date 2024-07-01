package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FloatingCarpetRecipeProvider extends RecipeProvider
{
    public FloatingCarpetRecipeProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput builder) {
        final Map<DyeColor, WoolCarpetBlock> carpets = new HashMap<>();
        carpets.put(((WoolCarpetBlock) Blocks.WHITE_CARPET).getColor(), (WoolCarpetBlock) Blocks.WHITE_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.LIGHT_GRAY_CARPET).getColor(), (WoolCarpetBlock) Blocks.LIGHT_GRAY_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.GRAY_CARPET).getColor(), (WoolCarpetBlock) Blocks.GRAY_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.BLACK_CARPET).getColor(), (WoolCarpetBlock) Blocks.BLACK_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.BROWN_CARPET).getColor(), (WoolCarpetBlock) Blocks.BROWN_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.RED_CARPET).getColor(), (WoolCarpetBlock) Blocks.RED_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.ORANGE_CARPET).getColor(), (WoolCarpetBlock) Blocks.ORANGE_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.YELLOW_CARPET).getColor(), (WoolCarpetBlock) Blocks.YELLOW_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.LIME_CARPET).getColor(), (WoolCarpetBlock) Blocks.LIME_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.GREEN_CARPET).getColor(), (WoolCarpetBlock) Blocks.GREEN_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.CYAN_CARPET).getColor(), (WoolCarpetBlock) Blocks.CYAN_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.LIGHT_BLUE_CARPET).getColor(), (WoolCarpetBlock) Blocks.LIGHT_BLUE_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.BLUE_CARPET).getColor(), (WoolCarpetBlock) Blocks.BLUE_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.PURPLE_CARPET).getColor(), (WoolCarpetBlock) Blocks.PURPLE_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.MAGENTA_CARPET).getColor(), (WoolCarpetBlock) Blocks.MAGENTA_CARPET);
        carpets.put(((WoolCarpetBlock) Blocks.PINK_CARPET).getColor(), (WoolCarpetBlock) Blocks.PINK_CARPET);

        for (final FloatingCarpetBlock block : ModBlocks.getInstance().getFloatingCarpets())
        {
            final DyeColor color = block.getColor();
            ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, block)
                    .requires(carpets.get(color))
                    .requires(Tags.Items.STRINGS)
                    .group("floating_carpets")
                    .unlockedBy("has_string", has(Tags.Items.STRINGS))
                    .unlockedBy("has_carpet", has(carpets.get(color)))
                    .save(builder);
        }
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Floating Carpet Recipe Provider";
    }
}
