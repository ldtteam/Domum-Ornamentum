package com.ldtteam.domumornamentum.block;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ICachedItemGroupBlock
{

    void fillItemCategory(final @NotNull NonNullList<ItemStack> items);

    void resetCache();
}
