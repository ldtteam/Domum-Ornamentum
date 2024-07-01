package com.ldtteam.domumornamentum.client.model.data;

import com.google.common.collect.ImmutableMap;
import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.util.Constants;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

import static com.ldtteam.domumornamentum.util.Constants.BLOCK_ENTITY_TEXTURE_DATA;

public record MaterialTextureData(Map<ResourceLocation, Block> texturedComponents)
{
    public static final MaterialTextureData EMPTY = new MaterialTextureData(Map.of());

    public static final Codec<MaterialTextureData> CODEC =
        Codec.unboundedMap(ResourceLocation.CODEC, BuiltInRegistries.BLOCK.byNameCodec())
            .xmap(MaterialTextureData::fromCodec, MaterialTextureData::texturedComponents);

    public static final StreamCodec<RegistryFriendlyByteBuf, MaterialTextureData> STREAM_CODEC = ByteBufCodecs
        .map((IntFunction<Map<ResourceLocation, Block>>) HashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.registry(Registries.BLOCK))
        .map(MaterialTextureData::fromCodec, MaterialTextureData::texturedComponents);

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

    @Deprecated(forRemoval = true, since = "1.21")
    public Map<ResourceLocation, Block> getTexturedComponents()
    {
        return texturedComponents;
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
            if (texturedComponents.containsKey(component.getId()))
            {
                localComponentsPresent++;
            }
        }

        if (localComponentsPresent == texturedComponents.size())
        {
            return this;
        }

        final Builder newData = new Builder();
        block.getComponents().forEach(comp -> newData.setComponent(comp.getId(), texturedComponents.get(comp.getId())));
        return newData.build();
    }

    /**
     * @deprecated use datacomponent, remove at 1.22
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
     * @deprecated use datacomponent, remove at 1.22
     */
    @Deprecated(forRemoval = true, since = "1.21")
    public static MaterialTextureData deserializeFromNBT(final CompoundTag nbt) {
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
     * @deprecated use datacomponent, remove at 1.22
     */
    @Deprecated(forRemoval = true, since = "1.21")
    public static MaterialTextureData deserializeFromItemStack(final ItemStack itemStack)
    {
        return itemStack.getOrDefault(IDomumOrnamentumApi.getInstance().getMaterialTextureComponentType(), MaterialTextureData.EMPTY);
    }

    /**
     * @deprecated use datacomponent, remove at 1.22
     */
    @Deprecated(forRemoval = true, since = "1.21")
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
     * @deprecated use datacomponent, remove at 1.22
     */
    @Deprecated(forRemoval = true, since = "1.21")
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

        public void putIntoItemStack(final ItemStack itemStack)
        {
            itemStack.set(IDomumOrnamentumApi.getInstance().getMaterialTextureComponentType(), build());
        }
    }
}
