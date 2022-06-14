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
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeItem;
import net.minecraftforge.registries.ForgeRegistries;
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
    public void run(@NotNull final CachedOutput cache) throws IOException
    {
        for (final ExtraBlock block : ModBlocks.getInstance().getExtraTopBlocks())
        {
            final ExtraBlockType type = block.getType();
            final ShapedPatternJson pattern =  new ShapedPatternJson("X X"," Z ","X X");
            final Map<String, RecipeIngredientKeyJson> keys = new HashMap<>();
            final ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(type.getMaterial());
            keys.put("X", new RecipeIngredientKeyJson(new RecipeIngredientJson(registryName.toString(), false)));
            keys.put("Z", new RecipeIngredientKeyJson(new RecipeIngredientJson(type.getColor() == null ? registryName.toString() : ForgeRegistries.ITEMS.getKey(DyeItem.byColor(type.getColor())).toString(), false)));

            final ShapedRecipeJson json = new ShapedRecipeJson("extra", pattern, keys, new RecipeResultJson(4, ForgeRegistries.ITEMS.getKey(block.asItem()).toString()));
            final Path recipeFolder = this.generator.getOutputFolder().resolve(DataGeneratorConstants.RECIPES_DIR);
            final Path blockstatePath = recipeFolder.resolve(block.getRegistryName().getPath() + ".json");

            DataProvider.saveStable(cache, DataGeneratorConstants.serialize(json), blockstatePath);
        }
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Extra Blocks Recipe Provider";
    }
}
