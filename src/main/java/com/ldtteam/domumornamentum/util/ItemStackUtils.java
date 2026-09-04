package com.ldtteam.domumornamentum.util;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ItemStackUtils {

    public static ItemStack getMateriallyTexturedItemStackFromPlayer(Player playerEntity) {
        final ItemStack mainHandStack = playerEntity.getMainHandItem();
        final ItemStack offHandStack = playerEntity.getOffhandItem();

        if ((!(mainHandStack.getItem() instanceof IDoItem mainHandDoItem) || !mainHandDoItem.renderPreview()) && (!(offHandStack.getItem() instanceof IDoItem offHandDoItem) || !offHandDoItem.renderPreview()))
        {
            return ItemStack.EMPTY;
        }

        if (mainHandStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof IMateriallyTexturedBlock) {
            return mainHandStack;
        }

        if (offHandStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof IMateriallyTexturedBlock) {
            return offHandStack;
        }

        return ItemStack.EMPTY;
    }

    @Nullable
    public static InteractionHand getHandWithMateriallyTexturedItemStackFromPlayer(Player player) {
        final ItemStack materialStack = getMateriallyTexturedItemStackFromPlayer(player);
        if (materialStack == player.getMainHandItem())
            return InteractionHand.MAIN_HAND;
        if (materialStack == player.getOffhandItem())
            return InteractionHand.OFF_HAND;
        return null;
    }
}
