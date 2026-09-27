package com.ldtteam.domumornamentum.junit.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.datagen.panel.TrapdoorTypeProperty;
import com.ldtteam.domumornamentum.datagen.post.PostTypeProperty;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorTypeProperty;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Round-trip coverage for the {@link RangeSelectItemModelProperty} implementations this mod registers:
 * <ul>
 *   <li>{@code domum_ornamentum:panel_type} — registered by {@code PanelModelProvider#registerProperty},</li>
 *   <li>{@code domum_ornamentum:trapdoor_type} — registered by {@code TrapdoorTypeProperty}'s model provider,</li>
 *   <li>{@code domum_ornamentum:fancy_trapdoor_type} — registered for the fancy doors,</li>
 *   <li>{@code domum_ornamentum:post_type} — registered by {@code PostModelProvider#registerProperty}.</li>
 * </ul>
 * Each property codec is a singleton ({@code MapCodec.unit}), so "encode/decode all enum values" reduces to:
 * <ol>
 *   <li>decoding an empty JSON object / NBT compound yields the registered singleton instance,</li>
 *   <li>encoding that singleton yields an empty container and round-trips back to it, and</li>
 *   <li>{@link RangeSelectItemModelProperty#type()} returns exactly the codec used for registration, so item
 *       model files referencing e.g. {@code "domum_ornamentum:trapdoor_type"} always resolve (this is what the
 *       old hardcoded-namespace datagen bug broke).</li>
 * </ol>
 * Note: resolution of the registered <em>names</em> happens in the client-side property registry, which is not
 * populated under a {@code DEDICATED_SERVER} JUnit boot; that side is covered by the game tests (Phase 2).
 */
class RangeSelectItemModelPropertyTest {

    private static Stream<Arguments> registeredProperties() {
        return Stream.of(
          Arguments.of("domum_ornamentum:panel_type", TrapdoorTypeProperty.CODEC, TrapdoorTypeProperty.INSTANCE),
          Arguments.of("domum_ornamentum:trapdoor_type", TrapdoorTypeProperty.CODEC, TrapdoorTypeProperty.INSTANCE),
          Arguments.of("domum_ornamentum:fancy_trapdoor_type", FancyTrapdoorTypeProperty.CODEC, FancyTrapdoorTypeProperty.INSTANCE),
          Arguments.of("domum_ornamentum:post_type", PostTypeProperty.CODEC, PostTypeProperty.INSTANCE));
    }

    @ParameterizedTest(name = "{0} round-trips through JSON")
    @MethodSource("registeredProperties")
    void jsonRoundTrip(final String registeredName, final MapCodec<?> codec, final RangeSelectItemModelProperty instance) {
        // decode empty container -> singleton
        final Object decoded = parseJson(codec, new JsonObject());
        assertThat(decoded).isSameAs(instance)
          .as("decoding an empty JSON object must yield the singleton registered as %s", registeredName);

        // encode singleton -> empty JSON object that decodes back to it
        final JsonObject json = encodeJson(codec, instance);
        assertThat(json.isEmpty()).as("a unit codec must serialize to an empty JSON object, got: %s", json).isTrue();

        assertThat(parseJson(codec, json)).isSameAs(instance);
    }

    @ParameterizedTest(name = "{0} round-trips through NBT")
    @MethodSource("registeredProperties")
    void nbtRoundTrip(final String registeredName, final MapCodec<?> codec, final RangeSelectItemModelProperty instance) {
        // decode empty container -> singleton
        final Object decoded = parseNbt(codec, new CompoundTag());
        assertThat(decoded).isSameAs(instance)
          .as("decoding an empty NBT compound must yield the singleton registered as %s", registeredName);

        // encode singleton -> empty compound that decodes back to it
        final CompoundTag nbt = encodeNbt(codec, instance);
        assertThat(nbt.isEmpty()).as("a unit codec must serialize to an empty compound, got: %s", nbt).isTrue();

        assertThat(parseNbt(codec, nbt)).isSameAs(instance);
    }

    @ParameterizedTest(name = "{0} type() is the registered codec")
    @MethodSource("registeredProperties")
    void typeReturnsTheCodecUsedForRegistration(final String registeredName, final MapCodec<?> codec, final RangeSelectItemModelProperty instance) {
        assertThat(instance.type())
          .as("%s#type() must be the very codec registered under that name", registeredName)
          .isSameAs(codec);
    }

    @SuppressWarnings("unchecked")
    private static <T> JsonObject encodeJson(final MapCodec<?> codec, final T instance) {
        final DataResult<JsonElement> encoded = ((MapCodec<T>) codec).codec().encodeStart(JsonOps.INSTANCE, instance);
        return (JsonObject) requireSuccess(encoded, "encoding to JSON");
    }

    @SuppressWarnings("unchecked")
    private static <T> Object parseJson(final MapCodec<?> codec, final JsonObject input) {
        final DataResult<T> parsed = ((MapCodec<T>) codec).codec().parse(JsonOps.INSTANCE, input);
        return requireSuccess(parsed, "parsing from JSON");
    }

    @SuppressWarnings("unchecked")
    private static <T> CompoundTag encodeNbt(final MapCodec<?> codec, final T instance) {
        final DataResult<net.minecraft.nbt.Tag> encoded = ((MapCodec<T>) codec).codec().encodeStart(NbtOps.INSTANCE, instance);
        return (CompoundTag) requireSuccess(encoded, "encoding to NBT");
    }

    @SuppressWarnings("unchecked")
    private static <T> Object parseNbt(final MapCodec<?> codec, final CompoundTag input) {
        final DataResult<T> parsed = ((MapCodec<T>) codec).codec().parse(NbtOps.INSTANCE, input);
        return requireSuccess(parsed, "parsing from NBT");
    }

    private static <T> T requireSuccess(final DataResult<T> result, final String context) {
        return result.getOrThrow(message -> new AssertionError(context + " failed: " + message));
    }
}
