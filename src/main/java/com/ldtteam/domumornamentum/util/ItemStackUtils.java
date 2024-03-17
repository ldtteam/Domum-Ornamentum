package com.ldtteam.domumornamentum.util;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ItemStackUtils {

    public static ItemStack getMateriallyTexturedItemStackFromPlayer(Player playerEntity) {
        final ItemStack mainHandStack = playerEntity.getMainHandItem();
        final ItemStack offHandStack = playerEntity.getOffhandItem();

        final Registry<Block> blockRegistry = BuiltInRegistries.BLOCK;
        final Registry<Item> itemRegistry = BuiltInRegistries.ITEM;

        final ResourceLocation mainHandItemLocation = itemRegistry.getKey(mainHandStack.getItem());
        final ResourceLocation offHandItemLocation = itemRegistry.getKey(offHandStack.getItem());

        if (blockRegistry.containsKey(mainHandItemLocation) && blockRegistry.get(mainHandItemLocation) instanceof IMateriallyTexturedBlock) {
            return mainHandStack;
        }

        if (blockRegistry.containsKey(offHandItemLocation) && blockRegistry.get(offHandItemLocation) instanceof IMateriallyTexturedBlock) {
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
