package com.ldtteam.domumornamentum.jei;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MaterialSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack>
{
    private static final MaterialSubtypeInterpreter INSTANCE = new MaterialSubtypeInterpreter();
    public static MaterialSubtypeInterpreter getInstance() { return INSTANCE; }

    private MaterialSubtypeInterpreter()
    {
    }

    @NotNull
    @Override
    public String apply(@NotNull final ItemStack itemStack,
                        @NotNull final UidContext context)
    {
        if (!itemStack.hasTag())
        {
            return IIngredientSubtypeInterpreter.NONE;
        }

        return itemStack.getTag().getString("type");
    }
}
