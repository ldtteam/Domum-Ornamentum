package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class ArchitectsCutterRecipeSerializer implements RecipeSerializer<ArchitectsCutterRecipe>
{
    @Override
    public ArchitectsCutterRecipe fromNetwork(final @NotNull FriendlyByteBuf buffer)
    {
        return new ArchitectsCutterRecipe(buffer.readResourceLocation(), buffer.readInt(), buffer.readOptional(FriendlyByteBuf::readNbt));
    }

    @Override
    public void toNetwork(final @NotNull FriendlyByteBuf buffer, final @NotNull ArchitectsCutterRecipe recipe)
    {
        buffer.writeResourceLocation(recipe.getBlockName());
        buffer.writeInt(recipe.getCount());
        buffer.writeOptional(recipe.getAdditionalTag(), FriendlyByteBuf::writeNbt);
    }

    @Override
    public Codec<ArchitectsCutterRecipe> codec()
    {
        return ArchitectsCutterRecipe.CODEC;
    }
}
