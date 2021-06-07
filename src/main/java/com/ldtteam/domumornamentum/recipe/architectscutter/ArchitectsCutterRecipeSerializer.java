package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class ArchitectsCutterRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ArchitectsCutterRecipe>
{
    @Override
    public ArchitectsCutterRecipe read(final ResourceLocation recipeId, final JsonObject json)
    {
        return new ArchitectsCutterRecipe(recipeId);
    }

    @Nullable
    @Override
    public ArchitectsCutterRecipe read(final ResourceLocation recipeId, final PacketBuffer buffer)
    {
        return new ArchitectsCutterRecipe(recipeId);
    }

    @Override
    public void write(final PacketBuffer buffer, final ArchitectsCutterRecipe recipe)
    {
    }
}
