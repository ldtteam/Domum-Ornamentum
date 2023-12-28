package com.ldtteam.domumornamentum.jei;
import com.ldtteam.domumornamentum.util.Constants;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
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
        final CompoundTag tag = itemStack.getTagElement(BlockItem.BLOCK_STATE_TAG);
        if (tag == null || !tag.contains(Constants.TYPE_BLOCK_PROPERTY, Tag.TAG_STRING))
        {
            return IIngredientSubtypeInterpreter.NONE;
        }

        return tag.getString(Constants.TYPE_BLOCK_PROPERTY);
    }
}
