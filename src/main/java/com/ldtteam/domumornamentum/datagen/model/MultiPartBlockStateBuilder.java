package com.ldtteam.domumornamentum.datagen.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MultiPartBlockStateBuilder {
    final Block block;
    private final BlockStateProvider provider;
    private final List<JsonObject> parts = new ArrayList<>();

    MultiPartBlockStateBuilder(BlockStateProvider provider, Block block) {
        this.provider = provider;
        this.block = block;
    }

    public ConfiguredModel.Builder<PartBuilder> part() {
        PartBuilder part = new PartBuilder();
        return ConfiguredModel.builder(part::setModel);
    }

    void add(PartBuilder part) {
        JsonObject json = new JsonObject();
        JsonObject when = conditionJson(part.conditions);
        if (!when.isEmpty()) {
            json.add("when", when);
        }

        JsonObject apply = new JsonObject();
        apply.addProperty("model", part.model.getLocation().toString());
        int x = normalizedRotation(part.rotationX);
        int y = normalizedRotation(part.rotationY);
        if (x != 0) {
            apply.addProperty("x", x);
        }
        if (y != 0) {
            apply.addProperty("y", y);
        }
        if (part.uvLock) {
            apply.addProperty("uvlock", true);
        }
        json.add("apply", apply);
        parts.add(json);
    }

    JsonObject toJson() {
        JsonObject root = new JsonObject();
        JsonArray multipart = new JsonArray();
        parts.forEach(multipart::add);
        root.add("multipart", multipart);
        return root;
    }

    static int normalizedRotation(int rotation) {
        return Math.floorMod(rotation, 360);
    }

    private static JsonObject conditionJson(Map<String, List<String>> conditions) {
        JsonObject when = new JsonObject();
        conditions.forEach((property, values) -> {
            if (values.size() == 1) {
                when.addProperty(property, values.getFirst());
                return;
            }

            JsonArray alternatives = new JsonArray();
            values.forEach(value -> {
                JsonObject alternative = new JsonObject();
                alternative.addProperty(property, value);
                alternatives.add(alternative);
            });
            when.add(property, alternatives);
        });
        return when;
    }

    public final class PartBuilder {
        private ModelFile model;
        private int rotationX;
        private int rotationY;
        private boolean uvLock;
        private final Map<String, List<String>> conditions = new LinkedHashMap<>();

        PartBuilder() {}

        PartBuilder setModel(ConfiguredModel configuredModel) {
            this.model = configuredModel.model();
            this.rotationX = configuredModel.rotationX();
            this.rotationY = configuredModel.rotationY();
            this.uvLock = configuredModel.uvLock();
            return this;
        }

        public PartBuilder rotationX(int rotation) {
            this.rotationX += rotation;
            return this;
        }

        public PartBuilder rotationY(int rotation) {
            this.rotationY += rotation;
            return this;
        }

        public PartBuilder uvLock(boolean uvLock) {
            this.uvLock |= uvLock;
            return this;
        }

        public PartBuilder addModel() {
            if (this.model == null) {
                throw new IllegalStateException("Multipart part has no model file");
            }
            return this;
        }

        @SafeVarargs
        public final <T extends Comparable<T>> PartBuilder condition(Property<T> property, T... values) {
            List<String> names = conditions.computeIfAbsent(property.getName(), key -> new ArrayList<>());
            for (T value : values) {
                // Use the property's serialized value instead of enum
                // toString(), which produces uppercase Java constant names.
                String serialized = property.getName(value);
                if (!names.contains(serialized)) {
                    names.add(serialized);
                }
            }
            return this;
        }

        public MultiPartBlockStateBuilder end() {
            addModel();
            add(this);
            return MultiPartBlockStateBuilder.this;
        }
    }
}
