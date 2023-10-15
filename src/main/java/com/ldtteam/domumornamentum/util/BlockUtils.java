package com.ldtteam.domumornamentum.util;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class BlockUtils
{

    private BlockUtils()
    {
        throw new IllegalStateException("Can not instantiate an instance of: BlockUtils. This is a utility class");
    }

    public static Component getHoverName(final Block block) {
        return new ItemStack(block).getHoverName();
    }

    public static List<ItemStack> getMaterializedItemStack(final @NotNull LootParams.Builder builder) {
        final ItemStack stack = getMaterializedItemStack(builder.getOptionalParameter(LootContextParams.THIS_ENTITY), builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY), (s, e) -> s);
        if (!stack.isEmpty())
            return Lists.newArrayList(stack);

        return Collections.emptyList();
    }

    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockGetter blockGetter, final BlockPos blockPos) {
        return getMaterializedItemStack(entity, blockGetter.getBlockEntity(blockPos), (s, e) -> s);
    }

    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockEntity blockEntity) {
        return getMaterializedItemStack(entity, blockEntity, (s, e) -> s);
    }

    public static List<ItemStack> getMaterializedItemStack(final @NotNull LootParams.Builder builder, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        final ItemStack stack = getMaterializedItemStack(builder.getOptionalParameter(LootContextParams.THIS_ENTITY), builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY), adapter);
        if (!stack.isEmpty())
            return Lists.newArrayList(stack);

        return Collections.emptyList();
    }

    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockGetter blockGetter, final BlockPos blockPos, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        return getMaterializedItemStack(entity, blockGetter.getBlockEntity(blockPos), adapter);
    }

    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockEntity blockEntity, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        if (!(blockEntity instanceof final MateriallyTexturedBlockEntity texturedBlockEntity))
            return ItemStack.EMPTY;

        final ItemStack result = new ItemStack(blockEntity.getBlockState().getBlock());
        texturedBlockEntity.saveToItem(result);

        return adapter.apply(result, (MateriallyTexturedBlockEntity) blockEntity);
    }
}
