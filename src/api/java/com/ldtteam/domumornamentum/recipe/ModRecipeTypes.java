package com.ldtteam.domumornamentum.recipe;

import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes
{
    public static RecipeType<ArchitectsCutterRecipe> ARCHITECTS_CUTTER = register("architects_cutter");

    private ModRecipeTypes()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModRecipeTypes. This is a utility class");
    }

    /**
     * Registers a new recipe type, prefixing with the mod ID
     * @param name  Recipe type name
     * @param <T>   Recipe type
     * @return  Registered recipe type
     */
    @SuppressWarnings("SameParameterValue")
    static <T extends Recipe<?>> RecipeType<T> register(String name) {
        return RecipeType.register(Constants.MOD_ID + ":" + name);
    }
}
