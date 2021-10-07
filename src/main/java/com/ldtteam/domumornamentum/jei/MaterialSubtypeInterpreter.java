package com.ldtteam.domumornamentum.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class MaterialSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack>
{
    private static final MaterialSubtypeInterpreter INSTANCE = new MaterialSubtypeInterpreter();
    public static MaterialSubtypeInterpreter getInstance() { return INSTANCE; }

    private MaterialSubtypeInterpreter()
    {
    }

    @Override
    public String apply(ItemStack itemStack, UidContext context)
    {
        if (!itemStack.hasTag())
        {
            return IIngredientSubtypeInterpreter.NONE;
        }

        return itemStack.getTag().getString("type");
    }
}
