package com.ldtteam.domumornamentum.client.model.data;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.ldtteam.domumornamentum.util.Constants.BLOCK_ENTITY_TEXTURE_DATA;

public class MaterialTextureData implements INBTSerializable<CompoundTag>
{
    public static final MaterialTextureData EMPTY = new MaterialTextureData(Map.of());

    private final Map<ResourceLocation, Block> texturedComponents;

    public MaterialTextureData()
    {
        this(new HashMap<>());
    }

    public MaterialTextureData(final Map<ResourceLocation, Block> texturedComponents) {
        this.texturedComponents = texturedComponents;
    }

    public Map<ResourceLocation, Block> getTexturedComponents()
    {
        return texturedComponents;
    }

    public MaterialTextureData setComponent(final ResourceLocation key, final Block value)
    {
        final MaterialTextureData textureData = this == EMPTY ? new MaterialTextureData() : this;
        textureData.texturedComponents.put(key, value);
        return this;
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
    public CompoundTag serializeNBT(final HolderLookup.Provider provider)
    {
        return serializeNBT();
    }

    public CompoundTag serializeNBT()
    {
        final CompoundTag nbt = new CompoundTag();

        if (isEmpty())
            return nbt;

        this.getTexturedComponents().forEach((key, value) -> nbt.putString(key.toString(), Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(value)).toString()));

        return nbt;
    }

    @Override
    public void deserializeNBT(final HolderLookup.Provider provider, final CompoundTag nbt)
    {
        this.texturedComponents.clear();

        nbt.getAllKeys().forEach(key -> {
            final ResourceLocation name = ResourceLocation.parse(nbt.getString(key));

            if (BuiltInRegistries.BLOCK.get(name) != Blocks.AIR)
            {
                this.texturedComponents.put(ResourceLocation.parse(key), BuiltInRegistries.BLOCK.get(name));
            }
        });
    }

    public static MaterialTextureData deserializeFromNBT(final CompoundTag nbt) {
        if (nbt == null || nbt.isEmpty())
            return EMPTY;

        final MaterialTextureData newData = new MaterialTextureData();
        newData.deserializeNBT(null, nbt);
        return newData;
    }

    @SuppressWarnings("deprecation")
    public static MaterialTextureData deserializeFromItemStack(final ItemStack itemStack)
    {
        return deserializeFromNBT(itemStack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY).getUnsafe().getCompound(BLOCK_ENTITY_TEXTURE_DATA));
    }

    public static void updateItemStack(final ItemStack itemStack, final Function<MaterialTextureData, MaterialTextureData> updater)
    {
        if (itemStack.getItem() instanceof final BlockItem bi && bi.getBlock() instanceof IMateriallyTexturedBlock)
        {
            final MaterialTextureData textureData = updater.apply(deserializeFromItemStack(itemStack));
            final CompoundTag tag = new CompoundTag();

            if (!textureData.isEmpty())
            {
                tag.put(BLOCK_ENTITY_TEXTURE_DATA, textureData.serializeNBT());
            }

            BlockItem.setBlockEntityData(itemStack,
                BuiltInRegistries.BLOCK_ENTITY_TYPE.get(Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE),
                tag);
        }
    }

    /**
     * @see BlockEntity#saveToItem(ItemStack, net.minecraft.core.HolderLookup.Provider)
     */
    public void writeToItemStack(final ItemStack itemStack)
    {
        updateItemStack(itemStack, old -> this);
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
