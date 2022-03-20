package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PillarShapeType implements StringRepresentable
{
    /**
     * these constants do not follow our naming conventions because forge does not allow the use of uppercase constants when creating an EnumProperty
     */
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
        return this.name;
    }


    public @NotNull String getName()
    {
        return this.name;
    }


}
