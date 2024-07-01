package com.ldtteam.domumornamentum.recipe.architectscutter;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ArchitectsCutterRecipeInput(Container items) implements RecipeInput
{
    @Override
    public ItemStack getItem(final int idx)
    {
        return items.getItem(idx);
    }

    @Override
    public int size()
    {
        return items.getContainerSize();
    }
}
