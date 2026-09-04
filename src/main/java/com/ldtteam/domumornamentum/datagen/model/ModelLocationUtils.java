package com.ldtteam.domumornamentum.datagen.model;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

final class ModelLocationUtils {
    private ModelLocationUtils() {}

    static Identifier getModelLocation(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    static Identifier getModelLocation(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
