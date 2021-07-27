package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.datagenerators.recipes.RecipeIngredientJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedPatternJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedRecipeJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.DyeItem;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ExtraRecipeProvider implements DataProvider
{
    private final DataGenerator generator;

    public ExtraRecipeProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        for (final ExtraBlock block : ModBlocks.getExtraTopBlocks())
        {
            final ExtraBlockType type = block.getType();
            final ShapedPatternJson pattern =  new ShapedPatternJson("X X"," Z ","X X");
            final Map<String, RecipeIngredientKeyJson> keys = new HashMap<>();
            keys.put("X", new RecipeIngredientKeyJson(new RecipeIngredientJson(type.getMaterial().getRegistryName().toString(), false)));
            keys.put("Z", new RecipeIngredientKeyJson(new RecipeIngredientJson(type.getColor() == null ? type.getMaterial().getRegistryName().toString() : DyeItem.byColor(type.getColor()).getRegistryName().toString(), false)));

            final ShapedRecipeJson json = new ShapedRecipeJson("extra", pattern, keys, new RecipeResultJson(4, block.asItem().getRegistryName().toString()));
            final Path recipeFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.RECIPES_DIR);
            final Path blockstatePath = recipeFolder.resolve(block.getRegistryName().getPath() + ".json");

            DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(json), blockstatePath);
        }
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Extra Blocks Recipe Provider";
    }
}
