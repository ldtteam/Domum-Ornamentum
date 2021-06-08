package com.ldtteam.domumornamentum.client.model.data;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Objects;

public class MaterialTextureData implements INBTSerializable<CompoundNBT>
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
        if (!(o instanceof MaterialTextureData))
        {
            return false;
        }
        final MaterialTextureData that = (MaterialTextureData) o;
        return Objects.equals(getTexturedComponents(), that.getTexturedComponents());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getTexturedComponents());
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        final CompoundNBT nbt = new CompoundNBT();

        if (this == EMPTY)
            return nbt;

        this.getTexturedComponents().forEach((key, value) -> {
            nbt.putString(key.toString(), Objects.requireNonNull(value.getRegistryName()).toString());
        });

        return nbt;
    }

    @Override
    public void deserializeNBT(final CompoundNBT nbt)
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

    public static MaterialTextureData deserializeFromNBT(final CompoundNBT nbt) {
        if (nbt.getAllKeys().isEmpty())
            return EMPTY;

        final MaterialTextureData newData = new MaterialTextureData();
        newData.deserializeNBT(nbt);
        return newData;
    }
}
