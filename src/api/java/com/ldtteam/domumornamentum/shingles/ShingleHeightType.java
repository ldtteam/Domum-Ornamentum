package com.ldtteam.domumornamentum.shingles;

public enum ShingleHeightType
{
    DEFAULT(""),
    FLAT("flat_"),
    FLAT_LOWER("flat_lower_");

    private final String id;

    ShingleHeightType(final String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
}