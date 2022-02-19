package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;

public enum PillarShapeType implements StringRepresentable
{
    pillar_base ("pillar_base"),
    pillar_capital ("pillar_capital"),
    pillar_column ("pillar_column"),
    full_pillar ("full_pillar");

    private final String name;

    PillarShapeType(String name)
    {
        this.name = name;
    }

    @Override
    public String getSerializedName()
    {
        return this.name();
    }
}
