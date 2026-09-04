package com.ldtteam.domumornamentum.datagen.model;

import net.minecraft.resources.Identifier;

public abstract class CustomLoaderBuilder<T extends ModelBuilder<T>> {
    protected final T parent;
    private final boolean allowInlineElements;

    protected CustomLoaderBuilder(Identifier loaderId, T parent, boolean allowInlineElements) {
        this.parent = parent;
        this.allowInlineElements = allowInlineElements;
        parent.getRoot().addProperty("loader", loaderId.toString());
    }

    public T end() {
        return parent.self();
    }
}
