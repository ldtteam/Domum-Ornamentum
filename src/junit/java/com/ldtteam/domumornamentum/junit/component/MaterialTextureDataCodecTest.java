package com.ldtteam.domumornamentum.junit.component;

import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Codec round-trip coverage for the {@code domum_ornamentum:texture_data} item data component, per test plan §4.1:
 * JSON codec, NBT persistence (through real {@link ItemStack} serialization), and the network stream codec.
 * <p>
 * The FML JUnit bootstrapper runs the mod on a dedicated-server boot, so all registries (vanilla blocks plus this
 * mod's) are populated — both vanilla- and mod-namespaced values can be exercised headlessly.
 */
class MaterialTextureDataCodecTest {

    private static final Identifier TOP_KEY = Identifier.tryParse("domum_ornamentum:test_top");
    private static final Identifier SIDE_KEY = Identifier.tryParse("domum_ornamentum:test_side");

    /** Non-empty data mixing a vanilla block and this mod's own block (cross-namespace coverage). */
    private static MaterialTextureData testData() {
        final Block panelBlock = BuiltInRegistries.BLOCK.getValue(Identifier.tryParse("domum_ornamentum:panel"));
        final var builder = new MaterialTextureData.Builder();
        builder.setComponent(TOP_KEY, Blocks.STONE);
        builder.setComponent(SIDE_KEY, panelBlock);
        return builder.build();
    }

    private static RegistryAccess registryAccess() {
        return RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
    }

    @Test
    @DisplayName("empty data round-trips through JSON as an empty object")
    void emptyJsonRoundTrip() {
        final JsonObject json = (JsonObject) requireSuccess(MaterialTextureData.CODEC.encodeStart(JsonOps.INSTANCE, MaterialTextureData.EMPTY));

        assertThat(json.isEmpty()).as("empty texture data must serialize to an empty JSON object, got: %s", json).isTrue();

        final MaterialTextureData decoded = MaterialTextureData.CODEC.parse(JsonOps.INSTANCE, new JsonObject()).getOrThrow();
        assertThat(decoded.components()).isEmpty();
    }

    @Test
    @DisplayName("empty data round-trips through NBT as an empty compound")
    void emptyNbtRoundTrip() {
        final DataResult<Tag> encoded = MaterialTextureData.CODEC.encodeStart(NbtOps.INSTANCE, MaterialTextureData.EMPTY);
        final CompoundTag nbt = (CompoundTag) requireSuccess(encoded);

        assertThat(nbt.isEmpty()).as("empty texture data must serialize to an empty compound, got: %s", nbt).isTrue();

        final MaterialTextureData decoded = MaterialTextureData.CODEC.parse(NbtOps.INSTANCE, new CompoundTag()).getOrThrow();
        assertThat(decoded.components()).isEmpty();
    }

    @Test
    @DisplayName("non-empty data round-trips through JSON using registered block names")
    void nonEmptyJsonRoundTripPreservesRegisteredNames() {
        final MaterialTextureData data = testData();

        final JsonObject json = (JsonObject) requireSuccess(MaterialTextureData.CODEC.encodeStart(JsonOps.INSTANCE, data));

        assertThat(json.get(TOP_KEY.toString()).getAsString()).isEqualTo("minecraft:stone");
        assertThat(json.get(SIDE_KEY.toString()).getAsString()).isEqualTo("domum_ornamentum:panel");

        final MaterialTextureData decoded = MaterialTextureData.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
        assertThat(decoded).isEqualTo(data);
    }

    @Test
    @DisplayName("non-empty data round-trips through NBT using registered block names")
    void nonEmptyNbtRoundTrip() {
        final MaterialTextureData data = testData();

        final CompoundTag nbt = (CompoundTag) requireSuccess(MaterialTextureData.CODEC.encodeStart(NbtOps.INSTANCE, data));

        assertThat(nbt.getString(TOP_KEY.toString())).as("top key should hold minecraft:stone").contains("minecraft:stone");
        assertThat(nbt.getString(SIDE_KEY.toString())).as("side key should hold domum_ornamentum:panel").contains("domum_ornamentum:panel");

        final MaterialTextureData decoded = MaterialTextureData.CODEC.parse(NbtOps.INSTANCE, nbt).getOrThrow();
        assertThat(decoded).isEqualTo(data);
    }

    @Test
    @DisplayName("non-empty data round-trips through the network stream codec")
    void streamCodecRoundTrip() {
        final MaterialTextureData data = testData();
        final RegistryAccess access = registryAccess();

        final var nettyOut = Unpooled.buffer();
        final var out = new RegistryFriendlyByteBuf(nettyOut, access);
        MaterialTextureData.STREAM_CODEC.encode(out, data);
        final byte[] payload = new byte[nettyOut.readableBytes()];
        nettyOut.getBytes(0, payload);

        final RegistryFriendlyByteBuf in = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(payload), access);
        final MaterialTextureData decoded = MaterialTextureData.STREAM_CODEC.decode(in);

        assertThat(decoded).isEqualTo(data);
    }

    @Test
    @DisplayName("the registered texture_data component uses these codecs and round-trips through them")
    void registeredComponentTypeRoundTrips() {
        final var type = ModDataComponents.TEXTURE_DATA.get();

        assertThat(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(type))
          .as("component must be registered under the expected id")
          .isEqualTo(Identifier.tryParse("domum_ornamentum:texture_data"));

        // JSON codec of the component type (what item models / save data use)
        final MaterialTextureData data = testData();
        final JsonObject json = (JsonObject) requireSuccess(type.codec().encodeStart(JsonOps.INSTANCE, data));
        assertThat(type.codec().parse(JsonOps.INSTANCE, json).getOrThrow()).isEqualTo(data);

        // stream codec of the component type (what network sync uses)
        final RegistryAccess access = registryAccess();
        final var nettyOut = Unpooled.buffer();
        final var out = new RegistryFriendlyByteBuf(nettyOut, access);
        type.streamCodec().encode(out, data);
        final byte[] payload = new byte[nettyOut.readableBytes()];
        nettyOut.getBytes(0, payload);

        final RegistryFriendlyByteBuf in = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(payload), access);
        assertThat(type.streamCodec().decode(in)).isEqualTo(data);
    }

    @Test
    @DisplayName("an item stack persists the component through ItemStack NBT serialization")
    void itemStackPersistsComponentThroughNbt() {
        // In a headless JUnit boot (no server start), item holders' default components are never bound,
        // and constructing an ItemStack reads them — bind an empty map first if needed.
        final var stoneHolder = BuiltInRegistries.ITEM.get(Identifier.tryParse("minecraft:stone")).orElseThrow();
        if (!stoneHolder.areComponentsBound()) {
            stoneHolder.bindComponents(DataComponentMap.EMPTY);
        }

        final var type = ModDataComponents.TEXTURE_DATA.get();
        final MaterialTextureData data = testData();

        final ItemStack stack = new ItemStack(Blocks.STONE);
        stack.set(type, data);

        final CompoundTag nbt = (CompoundTag) requireSuccess(ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, stack));
        assertThat(nbt.isEmpty()).as("the serialized item stack must not be empty").isFalse();

        final ItemStack reloaded = ItemStack.CODEC.parse(NbtOps.INSTANCE, nbt).getOrThrow();
        assertThat(reloaded.getOrDefault(type, MaterialTextureData.EMPTY))
          .as("component must survive a full item save/load cycle")
          .isEqualTo(data);
    }

    private static <T> T requireSuccess(final DataResult<T> result) {
        return result.getOrThrow(message -> new AssertionError("codec operation failed: " + message));
    }
}
