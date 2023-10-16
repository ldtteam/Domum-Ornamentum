package com.ldtteam.domumornamentum.util;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
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

    /**
     * @deprecated see {@link #getMaterializedDrops(LootParams.Builder, Property...)}
     */
    @Deprecated(forRemoval = true, since = "1.20.1")
    public static List<ItemStack> getMaterializedItemStack(final @NotNull LootParams.Builder builder) {
        return getMaterializedDrops(builder, new Property[0]);
    }

    /**
     * @deprecated see {@link #getMaterializedItemStack(BlockEntity, Property...)}
     */
    @Deprecated(forRemoval = true, since = "1.20.1")
    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockGetter blockGetter, final BlockPos blockPos) {
        return getMaterializedItemStack(entity, blockGetter.getBlockEntity(blockPos), (s, e) -> s);
    }

    /**
     * @deprecated see {@link #getMaterializedItemStack(BlockEntity, Property...)}
     */
    @Deprecated(forRemoval = true, since = "1.20.1")
    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockEntity blockEntity) {
        return getMaterializedItemStack(entity, blockEntity, (s, e) -> s);
    }

    /**
     * @deprecated see {@link #getMaterializedDrops(LootParams.Builder, Property...)}
     */
    @Deprecated(forRemoval = true, since = "1.20.1")
    public static List<ItemStack> getMaterializedItemStack(final @NotNull LootParams.Builder builder, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        final ItemStack stack = getMaterializedItemStack(builder.getOptionalParameter(LootContextParams.THIS_ENTITY), builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY), adapter);
        if (!stack.isEmpty())
            return Lists.newArrayList(stack);

        return Collections.emptyList();
    }

    /**
     * @deprecated see {@link #getMaterializedItemStack(BlockEntity, Property...)}
     */
    @Deprecated(forRemoval = true, since = "1.20.1")
    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockGetter blockGetter, final BlockPos blockPos, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        return getMaterializedItemStack(entity, blockGetter.getBlockEntity(blockPos), adapter);
    }

    /**
     * @deprecated see {@link #getMaterializedItemStack(BlockEntity, Property...)}
     */
    @Deprecated(forRemoval = true, since = "1.20.1")
    public static ItemStack getMaterializedItemStack(final Entity entity, final BlockEntity blockEntity, final BiFunction<ItemStack, MateriallyTexturedBlockEntity, ItemStack> adapter) {
        if (!(blockEntity instanceof final MateriallyTexturedBlockEntity texturedBlockEntity))
            return ItemStack.EMPTY;

        final ItemStack result = new ItemStack(blockEntity.getBlockState().getBlock());
        texturedBlockEntity.saveToItem(result);

        return adapter.apply(result, (MateriallyTexturedBlockEntity) blockEntity);
    }

    public static List<ItemStack> getMaterializedDrops(final LootParams.Builder builder, final Property<?>... blockStateProperties)
    {
        final ItemStack stack = getMaterializedItemStack(builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY), blockStateProperties);
        if (!stack.isEmpty())
            return List.of(stack);

        return Collections.emptyList();
    }

    public static ItemStack getMaterializedItemStack(final BlockEntity blockEntity, final Property<?>... blockStateProperties)
    {
        if (!(blockEntity instanceof final MateriallyTexturedBlockEntity texturedBlockEntity))
        {
            return ItemStack.EMPTY;
        }

        final BlockState blockState = texturedBlockEntity.getBlockState();
        final ItemStack result = new ItemStack(blockEntity.getBlockState().getBlock());
        texturedBlockEntity.saveToItem(result);

        if (blockStateProperties.length > 0)
        {
            final CompoundTag tag = result.getOrCreateTagElement(BlockItem.BLOCK_STATE_TAG);
            for (final Property<?> property : blockStateProperties) 
            {
                if (property != null)
                {
                    tag.putString(property.getName(), getValueAsString(blockState, property));
                }
            }
        }

        return result;
    }

    public static <T extends Comparable<T>> void putPropertyIntoBlockStateTag(final ItemStack itemStack, final Property<T> property, final T value)
    {
        itemStack.getOrCreateTagElement(BlockItem.BLOCK_STATE_TAG).putString(property.getName(), property.getName(value));
    }

    public static <T extends Comparable<T>> void putPropertyIntoBlockStateTag(final CompoundTag tag, final Property<T> property, final T value)
    {
        CompoundTag blockStateTag = tag.getCompound(BlockItem.BLOCK_STATE_TAG);

        if (!tag.contains(BlockItem.BLOCK_STATE_TAG, Tag.TAG_COMPOUND))
        {
            tag.put(BlockItem.BLOCK_STATE_TAG, blockStateTag);
        }
        
        blockStateTag.putString(property.getName(), property.getName(value));
    }

    public static <T extends Comparable<T>> T getPropertyFromBlockStateTag(final ItemStack itemStack, final Property<T> property, final T defaultValue)
    {
        final CompoundTag tag = itemStack.getTagElement(BlockItem.BLOCK_STATE_TAG);
        if (tag == null || !tag.contains(property.getName(), Tag.TAG_STRING))
        {
            return defaultValue;
        }
        return property.getValue(tag.getString(property.getName())).orElse(defaultValue);
    }

    public static <T extends Comparable<T>> String getValueAsString(final BlockState blockState, final Property<T> property)
    {
        return property.getName(blockState.getValue(property));
    }
}
