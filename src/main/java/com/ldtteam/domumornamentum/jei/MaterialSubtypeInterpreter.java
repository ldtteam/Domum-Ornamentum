package com.ldtteam.domumornamentum.jei;

import com.ldtteam.domumornamentum.util.Constants;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class MaterialSubtypeInterpreter implements ISubtypeInterpreter<ItemStack>
{
    private static final Object NONE = new Object();
    private static final MaterialSubtypeInterpreter INSTANCE = new MaterialSubtypeInterpreter();
    public static MaterialSubtypeInterpreter getInstance() { return INSTANCE; }

    private MaterialSubtypeInterpreter()
    {
    }

    @Override
    public @Nullable Object getSubtypeData(final ItemStack ingredient, final @NonNull UidContext context) {
        final var type = ingredient.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY)
                .properties()
                .get(Constants.TYPE_BLOCK_PROPERTY);

        return type != null ? type : NONE;
    }
}
