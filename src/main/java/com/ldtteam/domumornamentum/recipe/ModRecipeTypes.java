package com.ldtteam.domumornamentum.recipe;

import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public class ModRecipeTypes
{
    public static IRecipeType<ArchitectsCutterRecipe> ARCHITECTS_CUTTER = register("architects_cutter");

    /**
     * Registers a new recipe type, prefixing with the mod ID
     * @param name  Recipe type name
     * @param <T>   Recipe type
     * @return  Registered recipe type
     */
    @SuppressWarnings("SameParameterValue")
    static <T extends IRecipe<?>> IRecipeType<T> register(String name) {
        return IRecipeType.register(Constants.MOD_ID + ":" + name);
    }
}
