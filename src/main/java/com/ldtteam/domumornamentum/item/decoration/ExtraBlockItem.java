package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExtraBlockItem extends BlockItem
{
    private final ExtraBlock extraBlock;

    public ExtraBlockItem(final ExtraBlock blockIn, final Properties builder)
    {
        super(blockIn, builder);
        this.extraBlock = blockIn;
    }

    @Override
    public @NotNull Component getName(final @NotNull ItemStack stack)
    {
        DyeColor color = extraBlock.getType().getColor();
        return Component.translatable(Constants.MOD_ID + ".extra.name.format" + (color == null ? "" : "." + color.getName()), Component.translatable(extraBlock.getType().getMaterial().getDescriptionId()));
    }
}
