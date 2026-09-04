package com.ldtteam.domumornamentum.datagen.model;

import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class VariantBlockStateBuilder {
    final Block block;
    private final Map<String, JsonObject> variants = new LinkedHashMap<>();

    VariantBlockStateBuilder(Block block) {
        this.block = block;
    }

    public VariantBlockStateBuilder forAllStates(Function<BlockState, ConfiguredModel[]> generator) {
        return forAllStatesExcept(generator);
    }

    public VariantBlockStateBuilder forAllStatesExcept(
        Function<BlockState, ConfiguredModel[]> generator,
        Property<?>... excludedProperties
    ) {
        List<Property<?>> excluded = Arrays.asList(excludedProperties);
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            put(state, generator.apply(state), excluded);
        }
        return this;
    }

    private void put(BlockState state, ConfiguredModel[] configuredModels, List<Property<?>> excluded) {
        StringBuilder key = new StringBuilder();
        for (Property<?> property : state.getProperties()) {
            if (excluded.contains(property)) {
                continue;
            }
            if (!key.isEmpty()) {
                key.append(',');
            }
            // Blockstate JSON uses the property's serialized value. Calling
            // Comparable#toString on enum properties emits the Java constant
            // name (for example, FULL), which MC 26.2 cannot parse back from
            // the generated resource.
            key.append(property.getName()).append('=').append(serializedValue(property, state));
        }

        ConfiguredModel configuredModel = configuredModels[0];
        JsonObject variant = new JsonObject();
        variant.addProperty("model", configuredModel.model().getLocation().toString());
        int rotationX = MultiPartBlockStateBuilder.normalizedRotation(configuredModel.rotationX());
        int rotationY = MultiPartBlockStateBuilder.normalizedRotation(configuredModel.rotationY());
        if (rotationX != 0) {
            variant.addProperty("x", rotationX);
        }
        if (rotationY != 0) {
            variant.addProperty("y", rotationY);
        }
        if (configuredModel.uvLock()) {
            variant.addProperty("uvlock", true);
        }
        variants.put(key.toString(), variant);
    }

    JsonObject toJson() {
        JsonObject root = new JsonObject();
        JsonObject variantsJson = new JsonObject();
        variants.forEach(variantsJson::add);
        root.add("variants", variantsJson);
        return root;
    }

    private static <T extends Comparable<T>> String serializedValue(final Property<T> property, final BlockState state) {
        return property.getName(state.getValue(property));
    }
}
