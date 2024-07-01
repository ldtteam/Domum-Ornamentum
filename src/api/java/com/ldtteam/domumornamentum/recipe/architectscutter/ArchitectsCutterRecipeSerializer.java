package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ArchitectsCutterRecipeSerializer implements RecipeSerializer<ArchitectsCutterRecipe>
{

    @Override
    public MapCodec<ArchitectsCutterRecipe> codec()
    {
        return ArchitectsCutterRecipe.CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ArchitectsCutterRecipe> streamCodec()
    {
        return ArchitectsCutterRecipe.STREAM_CODEC;
    }
}
