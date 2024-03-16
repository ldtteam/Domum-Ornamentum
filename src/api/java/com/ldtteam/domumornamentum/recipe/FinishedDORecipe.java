package com.ldtteam.domumornamentum.recipe;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * DOFinished Recipe type to reduce clutter.
 */
public abstract class FinishedDORecipe implements FinishedRecipe
{
    @NotNull
    @Override
    public RecipeSerializer<?> getType()
    {
        return ModRecipeSerializers.ARCHITECTS_CUTTER.get();
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement()
    {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId()
    {
        return null;
    }
}
