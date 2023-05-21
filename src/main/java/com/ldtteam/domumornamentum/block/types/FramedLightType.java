package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

// Creates types for TimberFrame with different variants of wood and texture

public enum FramedLightType implements StringRepresentable
{
    VERTICAL("vertical_light", "Vertical"),
    CROSSED("crossed_light", "Crossed"),
    FRAMED("framed_light", "Framed"),
    HORIZONTAL("horizontal_light", "Horizontal"),
    LANTERN("fancy_light", "Fancy"),
    GLAZED( "glazed_light", "Glazed");

    private final String name;
    private final String langName;

    FramedLightType(final String name, final String langName)
    {
        this.name = name;
        this.langName = langName;
    }

    @Override
    public String getSerializedName()
    {
        return this.name;
    }

    @NotNull
    public String getName()
    {
        return this.name;
    }

    @NotNull
    public String getLangName()
    {
        return this.langName;
    }
}
