package com.ldtteam.domumornamentum.block.types;

import com.ldtteam.domumornamentum.util.EnumHelper;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
        return this.name;
    }

    public @NotNull String getSpecificationName()
    {
        return this.name + "_spec";
    }

    private static final Map<String, PillarShapeType> ID_MAP = EnumHelper.createMap(PillarShapeType.class);

    public static PillarShapeType fromString(final String s, final PillarShapeType defaultType)
    {
        return EnumHelper.fromString(ID_MAP, s, defaultType);
    }
}
