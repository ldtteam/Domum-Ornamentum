package com.ldtteam.domumornamentum.item.decoration;

import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
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
        return new TranslatableComponent(Constants.MOD_ID + ".extra.name.format", new TranslatableComponent(extraBlock.getType().getColor() == null ? "" : extraBlock.getType().getColor().getSerializedName()), new TranslatableComponent(extraBlock.getType().getMaterial().getDescriptionId()));
    }
}
