package com.ldtteam.domumornamentum.util;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BlockUtils
{

    private BlockUtils()
    {
        throw new IllegalStateException("Can not instantiate an instance of: BlockUtils. This is a utility class");
    }

    public static Component getHoverName(final Block block) {
        return new ItemStack(block).getHoverName();
    }

    public static List<ItemStack> getMaterializedItemStack(final @NotNull LootContext.Builder builder) {
        final ItemStack stack = getMaterializedItemStack(builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY), (s, e) -> s);
        if (!stack.isEmpty())
            return Lists.newArrayList(stack);

        return Collections.emptyList();
    }

    public static ItemStack getMaterializedItemStack(final BlockGetter blockGetter, final BlockPos blockPos) {
        return getMaterializedItemStack(blockGetter.getBlockEntity(blockPos), (s, e) -> s);
    }

    public static ItemStack getMaterializedItemStack(final BlockEntity blockEntity) {
        return getMaterializedItemStack(blockEntity, (s, e) -> s);
    }

    public static List<ItemStack> getMaterializedItemStack(final @NotNull LootContext.Builder builder, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        final ItemStack stack = getMaterializedItemStack(builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY), adapter);
        if (!stack.isEmpty())
            return Lists.newArrayList(stack);

        return Collections.emptyList();
    }

    public static ItemStack getMaterializedItemStack(final BlockGetter blockGetter, final BlockPos blockPos, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        return getMaterializedItemStack(blockGetter.getBlockEntity(blockPos), adapter);
    }

    public static ItemStack getMaterializedItemStack(final BlockEntity blockEntity, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        if (!(blockEntity instanceof final MateriallyTexturedBlockEntity texturedBlockEntity))
            return ItemStack.EMPTY;

        final MaterialTextureData materialTextureData = texturedBlockEntity.getTextureData();

        final CompoundTag textureNbt = materialTextureData.serializeNBT();

        final ItemStack result = new ItemStack(blockEntity.getBlockState().getBlock());
        result.getOrCreateTag().put("textureData", textureNbt);

        return adapter.apply(result, (MateriallyTexturedBlockEntity) blockEntity);
    }
}
