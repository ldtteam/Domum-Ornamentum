package com.ldtteam.domumornamentum.datagen.model;

import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;

public class ModelFile {
    private final Identifier location;
    private final JsonObject root = new JsonObject();

    ModelFile(Identifier location) {
        this.location = location;
    }

    public static ModelFile unmanaged(Identifier location) {
        return new ModelFile(location);
    }

    public Identifier getLocation() {
        return location;
    }

    JsonObject getRoot() {
        return root;
    }
}
