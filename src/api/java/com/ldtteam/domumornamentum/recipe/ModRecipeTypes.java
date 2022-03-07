package com.ldtteam.domumornamentum.recipe;

import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes
{
    public static RecipeType<ArchitectsCutterRecipe> ARCHITECTS_CUTTER;

    private ModRecipeTypes()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModRecipeTypes. This is a utility class");
    }

    public static void init()
    {
        ARCHITECTS_CUTTER = register("architects_cutter");
    }

    /**
     * Registers a new recipe type, prefixing with the mod ID
     * @param name  Recipe type name
     * @param <T>   Recipe type
     * @return  Registered recipe type
     */
    @SuppressWarnings("SameParameterValue")
    static <T extends Recipe<?>> RecipeType<T> register(String name) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(Constants.MOD_ID, name), new RecipeType<T>() {
            public String toString() {
                return name;
            }
        });
    }
}
