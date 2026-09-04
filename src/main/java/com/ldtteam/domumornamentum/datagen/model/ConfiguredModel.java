package com.ldtteam.domumornamentum.datagen.model;

import java.util.function.Function;

public record ConfiguredModel(ModelFile model, int rotationX, int rotationY, boolean uvLock) {
    public static <R> Builder<R> builder(Function<ConfiguredModel, R> finisher) {
        return new Builder<>(finisher);
    }

    public static Builder<ConfiguredModel[]> builder() {
        return new Builder<>(model -> new ConfiguredModel[] { model });
    }

    public static final class Builder<R> {
        private final Function<ConfiguredModel, R> finisher;
        private ModelFile model;
        private int rotationX;
        private int rotationY;
        private boolean uvLock;

        private Builder(Function<ConfiguredModel, R> finisher) {
            this.finisher = finisher;
        }

        public Builder<R> modelFile(ModelFile model) {
            this.model = model;
            return this;
        }

        public Builder<R> rotationX(int rotation) {
            this.rotationX = rotation;
            return this;
        }

        public Builder<R> rotationY(int rotation) {
            this.rotationY = rotation;
            return this;
        }

        public Builder<R> uvLock(boolean uvLock) {
            this.uvLock = uvLock;
            return this;
        }

        public R addModel() {
            return build();
        }

        public R build() {
            if (model == null) {
                throw new IllegalStateException("Configured model has no model file");
            }
            return finisher.apply(new ConfiguredModel(model, rotationX, rotationY, uvLock));
        }
    }
}
