package com.ldtteam.domumornamentum.datagen.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class ModelBuilder<T extends ModelBuilder<T>> extends ModelFile {
    protected ModelBuilder(Identifier location) {
        super(location);
    }

    @SuppressWarnings("unchecked")
    final T self() {
        return (T) this;
    }

    public T parent(ModelFile parent) {
        getRoot().addProperty("parent", parent.getLocation().toString());
        return self();
    }

    public T parent(Identifier parent) {
        getRoot().addProperty("parent", parent.toString());
        return self();
    }

    public <L extends CustomLoaderBuilder<T>> L customLoader(Function<T, L> factory) {
        return factory.apply(self());
    }

    public TransformsBuilder transforms() {
        return new TransformsBuilder();
    }

    public OverrideBuilder override() {
        return new OverrideBuilder();
    }

    public class TransformsBuilder {
        private final Map<ItemDisplayContext, TransformBuilder> transforms = new LinkedHashMap<>();

        public TransformBuilder transform(ItemDisplayContext context) {
            return transforms.computeIfAbsent(context, key -> new TransformBuilder(this));
        }

        public T end() {
            if (!transforms.isEmpty()) {
                JsonObject display = new JsonObject();
                transforms.forEach((context, transform) -> display.add(context.getSerializedName(), transform.toJson()));
                getRoot().add("display", display);
            }
            return self();
        }
    }

    public final class TransformBuilder {
        private final TransformsBuilder transformsOwner;

        private TransformBuilder(final TransformsBuilder transformsOwner) {
            this.transformsOwner = transformsOwner;
        }

        private float rotationX;
        private float rotationY;
        private float rotationZ;
        private float translationX;
        private float translationY;
        private float translationZ;
        private float scaleX = 1.0F;
        private float scaleY = 1.0F;
        private float scaleZ = 1.0F;

        public TransformBuilder rotation(float x, float y, float z) {
            this.rotationX = x;
            this.rotationY = y;
            this.rotationZ = z;
            return this;
        }

        public TransformBuilder translation(float x, float y, float z) {
            this.translationX = x;
            this.translationY = y;
            this.translationZ = z;
            return this;
        }

        public TransformBuilder scale(float scale) {
            return scale(scale, scale, scale);
        }

        public TransformBuilder scale(float x, float y, float z) {
            this.scaleX = x;
            this.scaleY = y;
            this.scaleZ = z;
            return this;
        }

        JsonObject toJson() {
            JsonObject json = new JsonObject();
            addArray(json, "rotation", rotationX, rotationY, rotationZ);
            addArray(json, "translation", translationX, translationY, translationZ);
            addArray(json, "scale", scaleX, scaleY, scaleZ);
            return json;
        }

        public TransformsBuilder end() {
            return this.transformsOwner;
        }

        private void addArray(JsonObject json, String name, float x, float y, float z) {
            com.google.gson.JsonArray array = new com.google.gson.JsonArray();
            array.add(x);
            array.add(y);
            array.add(z);
            json.add(name, array);
        }
    }

    public final class OverrideBuilder {
        private final JsonObject override = new JsonObject();

        public OverrideBuilder predicate(Identifier property, float predicateValue) {
            JsonObject predicates = override.has("predicate")
                ? override.getAsJsonObject("predicate")
                : new JsonObject();
            predicates.addProperty(property.toString(), predicateValue);
            override.add("predicate", predicates);
            return this;
        }

        public OverrideBuilder model(ModelFile model) {
            override.addProperty("model", model.getLocation().toString());
            return this;
        }

        public T end() {
            JsonArray overrides = getRoot().has("overrides")
                ? getRoot().getAsJsonArray("overrides")
                : new JsonArray();
            overrides.add(override);
            getRoot().add("overrides", overrides);
            return self();
        }
    }
}
