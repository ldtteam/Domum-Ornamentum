package com.ldtteam.domumornamentum.block.types;

import com.ldtteam.domumornamentum.util.EnumHelper;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Shape types used by the Shingle Slabs.
 *
 * IF YOU CHANGE THIS FILE, OR ADD ENTRIES, RUN THE DATA GENERATORS.
 *
 *  -> gradle runData
 */
public enum ShingleSlabShapeType implements StringRepresentable
{
    TOP("top"),
    ONE_WAY("one_way"),
    TWO_WAY("two_way"),
    THREE_WAY("three_way"),
    FOUR_WAY("four_way"),
    CURVED("curved");

    private final String name;

    ShingleSlabShapeType(final String nameIn)
    {
        this.name = nameIn;
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

    private static final Map<String, ShingleSlabShapeType> ID_MAP = EnumHelper.createMap(ShingleSlabShapeType.class);

    public static ShingleSlabShapeType fromString(final String s, final ShingleSlabShapeType defaultType)
    {
        return EnumHelper.fromString(ID_MAP, s, defaultType);
    }
}
