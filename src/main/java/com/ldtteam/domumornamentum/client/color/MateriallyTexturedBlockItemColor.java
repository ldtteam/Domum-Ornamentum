package com.ldtteam.domumornamentum.client.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.Nullable;

public class MateriallyTexturedBlockItemColor implements ItemTintSource {
    public static final MateriallyTexturedBlockItemColor INSTANCE = new MateriallyTexturedBlockItemColor();
    public static final MapCodec<MateriallyTexturedBlockItemColor> MAP_CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    public int calculate(final ItemStack stack, @Nullable final ClientLevel level, @Nullable final LivingEntity owner) {
        return 0xffffffff;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
