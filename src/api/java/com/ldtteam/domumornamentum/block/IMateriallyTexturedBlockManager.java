package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import net.minecraft.world.item.ItemStack;

public interface IMateriallyTexturedBlockManager
{
    static IMateriallyTexturedBlockManager getInstance() {
        return IDomumOrnamentumApi.getInstance().getMateriallyTexturedBlockManager();
    }

    int getMaxTexturableComponentCount();

    boolean doesItemStackContainsMaterialForSlot(int slotIndex, ItemStack stack);
}
