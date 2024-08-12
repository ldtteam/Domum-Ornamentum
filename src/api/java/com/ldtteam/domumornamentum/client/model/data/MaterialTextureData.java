package com.ldtteam.domumornamentum.client.model.data;

import com.google.common.collect.ImmutableMap;
import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

public record MaterialTextureData(Map<ResourceLocation, Block> getTexturedComponents)
{
    public static final MaterialTextureData EMPTY = new MaterialTextureData(Map.of());

    public static final Codec<MaterialTextureData> CODEC =
        Codec.unboundedMap(ResourceLocation.CODEC, BuiltInRegistries.BLOCK.byNameCodec())
            .xmap(MaterialTextureData::fromCodec, MaterialTextureData::getTexturedComponents);

    public static final StreamCodec<RegistryFriendlyByteBuf, MaterialTextureData> STREAM_CODEC =
        ByteBufCodecs
            .map((IntFunction<Map<ResourceLocation, Block>>) HashMap::new,
                ResourceLocation.STREAM_CODEC,
                ByteBufCodecs.registry(Registries.BLOCK))
            .map(MaterialTextureData::fromCodec, MaterialTextureData::getTexturedComponents);

    /**
     * Ensures emptiness and mutability
     */
    private static MaterialTextureData fromCodec(final Map<ResourceLocation, Block> texturedComponents)
    {
        return texturedComponents.isEmpty() ? EMPTY : new MaterialTextureData(texturedComponents);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * @return new instance if old instance contained components not present on given block
     */
    public MaterialTextureData retainComponentsFromBlock(final IMateriallyTexturedBlock block)
    {
        // assume that in majority events all components match
        int localComponentsPresent = 0;
        for (final IMateriallyTexturedBlockComponent component : block.getComponents())
        {
            if (getTexturedComponents().containsKey(component.getId()))
            {
                localComponentsPresent++;
            }
        }

        if (localComponentsPresent == getTexturedComponents().size())
        {
            return this;
        }

        final Builder newData = new Builder();
        block.getComponents().forEach(comp -> newData.setComponent(comp.getId(), getTexturedComponents().get(comp.getId())));
        return newData.build();
    }

    /**
     * @deprecated use datacomponent or codec, remove at 1.22
     */
    @Deprecated(forRemoval = true, since = "1.21")
    public CompoundTag serializeNBT()
    {
        final CompoundTag nbt = new CompoundTag();

        if (isEmpty())
            return nbt;

        this.getTexturedComponents().forEach((key, value) -> nbt.putString(key.toString(), Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(value)).toString()));

        return nbt;
    }

    /**
     * @deprecated use datacomponent or codec, remove at 1.22
     */
    @Deprecated(forRemoval = true, since = "1.21")
    public static MaterialTextureData deserializeFromNBT(final CompoundTag nbt)
    {
        if (nbt == null || nbt.isEmpty())
            return EMPTY;

        final Builder newData = new Builder();
        nbt.getAllKeys().forEach(key -> {
            final ResourceLocation name = ResourceLocation.parse(nbt.getString(key));

            if (BuiltInRegistries.BLOCK.get(name) != Blocks.AIR)
            {
                newData.setComponent(ResourceLocation.parse(key), BuiltInRegistries.BLOCK.get(name));
            }
        });
        return newData.build();
    }

    /**
     * Writes this textureData into given itemStack.
     * 
     * @see BlockEntity#saveToItem(ItemStack, net.minecraft.core.HolderLookup.Provider)
     */
    public void writeToItemStack(final ItemStack itemStack)
    {
        itemStack.set(IDomumOrnamentumApi.getInstance().getMaterialTextureComponentType(), this.isEmpty() ? EMPTY : this);
    }

    /**
     * @return textureData stored in given itemStack (or empty instance)
     */
    public static MaterialTextureData readFromItemStack(final ItemStack itemStack)
    {
        return itemStack.getOrDefault(IDomumOrnamentumApi.getInstance().getMaterialTextureComponentType(), MaterialTextureData.EMPTY);
    }

    /**
     * Performs updating of textureData in given itemStack
     */
    public static void updateItemStack(final ItemStack itemStack, final UnaryOperator<MaterialTextureData> updater)
    {
        updater.apply(readFromItemStack(itemStack)).writeToItemStack(itemStack);
    }

    public boolean isEmpty()
    {
        return this == EMPTY || this.equals(EMPTY);
    }

    /**
     * Simple immutable textureData builder
     */
    public static class Builder
    {
        private final ImmutableMap.Builder<ResourceLocation, Block> texturedComponents = ImmutableMap.builder();

        public Builder setComponent(final ResourceLocation key, final Block value)
        {
            texturedComponents.put(key, value);
            return this;
        }

        public MaterialTextureData build()
        {
            return new MaterialTextureData(texturedComponents.build());
        }

        public void writeToItemStack(final ItemStack itemStack)
        {
            itemStack.set(IDomumOrnamentumApi.getInstance().getMaterialTextureComponentType(), build());
        }
    }
}
