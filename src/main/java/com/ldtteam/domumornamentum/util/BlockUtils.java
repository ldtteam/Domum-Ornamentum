package com.ldtteam.domumornamentum.util;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.AbstractMateriallyTexturedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
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

    public static List<ItemStack> getMaterializedItemStack(final @NotNull LootParams.Builder builder, final BiFunction<ItemStack, AbstractMateriallyTexturedBlockEntity, ItemStack> adapter) {
        final ItemStack stack = getMaterializedItemStack(builder.getOptionalParameter(LootContextParams.THIS_ENTITY), builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY), adapter);
        if (!stack.isEmpty())
            return Lists.newArrayList(stack);

        return Collections.emptyList();
    }

    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockGetter blockGetter, final BlockPos blockPos, final BiFunction<ItemStack, AbstractMateriallyTexturedBlockEntity, ItemStack> adapter) {
        return getMaterializedItemStack(entity, blockGetter.getBlockEntity(blockPos), adapter);
    }

    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockEntity blockEntity, final BiFunction<ItemStack, AbstractMateriallyTexturedBlockEntity, ItemStack> adapter) {
        if (!(blockEntity instanceof final AbstractMateriallyTexturedBlockEntity texturedBlockEntity))
            return ItemStack.EMPTY;

        final MaterialTextureData materialTextureData = texturedBlockEntity.getTextureData();

        final CompoundTag textureNbt = materialTextureData.serializeNBT();

        final ItemStack result = new ItemStack(blockEntity.getBlockState().getBlock());
        result.getOrCreateTag().put("textureData", textureNbt);

        return adapter.apply(result, (AbstractMateriallyTexturedBlockEntity) blockEntity);
    }

    public static <T extends Enum<T> & StringRepresentable> T getPropertyFromBlockStateTag(
        final ItemStack itemStack,
        final EnumProperty<T> property,
        final T defaultValue)
    {
        final T blockValue = property.getValue(itemStack.getOrCreateTag().get("type").toString().toLowerCase(Locale.ROOT).replace("\"", "")).get();
        return blockValue == null ? defaultValue : blockValue;
    }
}
