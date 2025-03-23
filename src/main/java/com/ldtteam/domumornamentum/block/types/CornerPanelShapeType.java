package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CornerPanelShapeType implements StringRepresentable
{
    UPPER("upper"),
    CENTER("center"),
    LOWER("lower");

    private final String serializationName;

    CornerPanelShapeType(final String serializationName)
    {
        this.serializationName = serializationName;
    }

    @Override
    @NotNull
    public String getSerializedName()
    {
        return serializationName;
    }
}
