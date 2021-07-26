package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.google.gson.JsonObject;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class ArchitectsCutterRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ArchitectsCutterRecipe>
{
    @Override
    public ArchitectsCutterRecipe fromJson(final ResourceLocation recipeId, final JsonObject json)
    {
        return new ArchitectsCutterRecipe(recipeId);
    }

    @Nullable
    @Override
    public ArchitectsCutterRecipe fromNetwork(final ResourceLocation recipeId, final FriendlyByteBuf buffer)
    {
        return new ArchitectsCutterRecipe(recipeId);
    }

    @Override
    public void toNetwork(final FriendlyByteBuf buffer, final ArchitectsCutterRecipe recipe)
    {
    }
}
