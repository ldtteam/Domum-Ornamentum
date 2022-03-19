package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PillarShapeType implements StringRepresentable
{
    PILLAR_BASE("pillar_base"),
    PILLAR_CAPITAL("pillar_capital"),
    PILLAR_COLUMN("pillar_column"),
    FULL_PILLAR("full_pillar");

    private final String name;

    PillarShapeType(String name)
    {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName()
    {
        return this.name();
    }
}
