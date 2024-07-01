package com.ldtteam.domumornamentum.component;

import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.Constants;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents
{
    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister.createDataComponents(Constants.MOD_ID);

    public static DeferredHolder<DataComponentType<?>, DataComponentType<MaterialTextureData>> TEXTURE_DATA =
        savedSynced("texture_data", MaterialTextureData.CODEC, MaterialTextureData.STREAM_CODEC);

    private static <D> DeferredHolder<DataComponentType<?>, DataComponentType<D>> savedSynced(final String name,
        final Codec<D> codec,
        final StreamCodec<RegistryFriendlyByteBuf, D> streamCodec)
    {
        return REGISTRY.registerComponentType(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }
}
