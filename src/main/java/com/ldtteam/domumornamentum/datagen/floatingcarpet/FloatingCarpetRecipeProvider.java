package com.ldtteam.domumornamentum.datagen.floatingcarpet;

import com.ldtteam.datagenerators.recipes.RecipeIngredientJson;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedPatternJson;
import com.ldtteam.datagenerators.recipes.shaped.ShapedRecipeJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FloatingCarpetRecipeProvider implements DataProvider
{
    private final DataGenerator generator;

    public FloatingCarpetRecipeProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull final HashCache cache) throws IOException
    {
        for (final FloatingCarpetBlock block : ModBlocks.getInstance().getFloatingCarpets())
        {
            final DyeColor color = block.getColor();
            final ShapedPatternJson pattern =  new ShapedPatternJson("C  ","S  ","   ");
            final Map<String, RecipeIngredientKeyJson> keys = new HashMap<>();
            keys.put("C", new RecipeIngredientKeyJson(new RecipeIngredientJson("minecraft:" + color.getName() + "_carpet", false)));
            keys.put("S", new RecipeIngredientKeyJson(new RecipeIngredientJson(Tags.Items.STRING.toString(), true)));

            final ShapedRecipeJson json = new ShapedRecipeJson("floating_carpets", pattern, keys, new RecipeResultJson(4, block.asItem().getRegistryName().toString()));
            final Path recipeFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.RECIPES_DIR);
            final Path blockstatePath = recipeFolder.resolve(block.getRegistryName().getPath() + ".json");

            DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(json), blockstatePath);
        }
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Floating Carpet Recipe Provider";
    }
}
