package com.ldtteam.domumornamentum.jei;

import com.ldtteam.domumornamentum.util.Constants;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
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
        return itemStack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY)
            .properties()
            .getOrDefault(Constants.TYPE_BLOCK_PROPERTY, IIngredientSubtypeInterpreter.NONE);
    }
}
