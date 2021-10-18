package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.datagenerators.recipes.RecipeIngredientJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedPatternJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedRecipeJson;
import com.ldtteam.datagenerators.recipes.shapeless.ShapelessRecipeJson;
import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrickRecipeProvider implements DataProvider
{
    private final DataGenerator generator;

    public BrickRecipeProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        for (final BrickBlock block : ModBlocks.getInstance().getBricks())
        {
            final List<RecipeIngredientKeyJson> keys = List.of(
              new RecipeIngredientKeyJson(new RecipeIngredientJson(block.getType().getIngredient().getRegistryName().toString(), false)),
              new RecipeIngredientKeyJson(new RecipeIngredientJson(block.getType().getIngredient().getRegistryName().toString(), false)),
              new RecipeIngredientKeyJson(new RecipeIngredientJson(block.getType().getIngredient2().getRegistryName().toString(), false)),
              new RecipeIngredientKeyJson(new RecipeIngredientJson(block.getType().getIngredient2().getRegistryName().toString(), false))
            );

            final ShapelessRecipeJson json = new ShapelessRecipeJson("brick", keys, new RecipeResultJson(4, block.asItem().getRegistryName().toString()));
            final Path recipeFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.RECIPES_DIR);
            final Path blockstatePath = recipeFolder.resolve(block.getRegistryName().getPath() + ".json");

            DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(json), blockstatePath);
        }
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Brick Blocks Recipe Provider";
    }
}
