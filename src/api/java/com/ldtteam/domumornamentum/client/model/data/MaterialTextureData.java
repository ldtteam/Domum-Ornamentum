package com.ldtteam.domumornamentum.client.model.data;

import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

import static com.ldtteam.domumornamentum.util.Constants.BLOCK_ENTITY_TEXTURE_DATA;

public class MaterialTextureData implements INBTSerializable<CompoundTag>
{
    public static final MaterialTextureData EMPTY = new MaterialTextureData();

    private final Map<ResourceLocation, Block> texturedComponents;

    public MaterialTextureData()
    {
        this.texturedComponents = Maps.newHashMap();
    }

    public MaterialTextureData(final Map<ResourceLocation, Block> texturedComponents) {
        this.texturedComponents = texturedComponents;
    }

    public Map<ResourceLocation, Block> getTexturedComponents()
    {
        return texturedComponents;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof final MaterialTextureData that))
        {
            return false;
        }
        return Objects.equals(getTexturedComponents(), that.getTexturedComponents());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getTexturedComponents());
    }

    @Override
    public CompoundTag serializeNBT()
    {
        final CompoundTag nbt = new CompoundTag();

        if (this == EMPTY)
            return nbt;

        this.getTexturedComponents().forEach((key, value) -> nbt.putString(key.toString(), Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(value)).toString()));

        return nbt;
    }

    @Override
    public void deserializeNBT(final CompoundTag nbt)
    {
        this.texturedComponents.clear();

        nbt.getAllKeys().forEach(key -> {
            final ResourceLocation name = new ResourceLocation(nbt.getString(key));

            if (ForgeRegistries.BLOCKS.getValue(name) != Blocks.AIR)
            {
                this.texturedComponents.put(new ResourceLocation(key), ForgeRegistries.BLOCKS.getValue(name));
            }
        });
    }

    public static MaterialTextureData deserializeFromNBT(final CompoundTag nbt) {
        if (nbt == null || nbt.getAllKeys().isEmpty())
            return EMPTY;

        final MaterialTextureData newData = new MaterialTextureData();
        newData.deserializeNBT(nbt);
        return newData;
    }

    public static MaterialTextureData deserializeFromItemStack(final ItemStack itemStack)
    {
        return deserializeFromNBT(extractNbtFromItemStack(itemStack));
    }

    @Nullable
    public static CompoundTag extractNbtFromItemStack(final ItemStack itemStack)
    {
        if (itemStack == null || !itemStack.hasTag() || !(itemStack.getItem() instanceof BlockItem))
        {
            return null;
        }

        final CompoundTag beTag = BlockItem.getBlockEntityData(itemStack);

        if (beTag == null || beTag.isEmpty() || !beTag.contains(BLOCK_ENTITY_TEXTURE_DATA, Tag.TAG_COMPOUND))
        {
            return null;
        }

        return beTag.getCompound(BLOCK_ENTITY_TEXTURE_DATA);
    }

    /**
     * @see BlockEntity#saveToItem(ItemStack)
     */
    public void writeToItemStack(final ItemStack itemStack)
    {
        if (this != EMPTY && !itemStack.isEmpty() && itemStack.getItem() instanceof BlockItem)
        {
            final CompoundTag tag = new CompoundTag();
            tag.put(BLOCK_ENTITY_TEXTURE_DATA, serializeNBT());
            BlockItem.setBlockEntityData(itemStack,
                ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(new ResourceLocation(Constants.MOD_ID, Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE)),
                tag);
        }
    }

    public boolean isEmpty()
    {
        return this.equals(EMPTY);
    }

    @Override
    public String toString()
    {
        return "MaterialTextureData{" +
                 "texturedComponents=" + texturedComponents +
                 '}';
    }
}
