package com.ldtteam.domumornamentum.block.types;

import com.ldtteam.domumornamentum.block.decorative.PaperWallBlock;
import com.ldtteam.domumornamentum.util.EnumHelper;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Types that the {@link PaperWallBlock} supports
 */
public enum PaperwallType implements StringRepresentable
{
    OAK("oak"),
    SPRUCE("spruce"),
    BIRCH("birch"),
    JUNGLE("jungle"),
    ACACIA("acacia"),
    DARK_OAK("dark_oak"),
    CRIMSON("crimson"),
    WARPED("warped"),
    CACTUS("cactus");

    private final String name;

    PaperwallType(final String nameIn)
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

    private static final Map<String, PaperwallType> ID_MAP = EnumHelper.createMap(PaperwallType.class);

    public static PaperwallType fromString(final String s, final PaperwallType defaultType)
    {
        return EnumHelper.fromString(ID_MAP, s, defaultType);
    }
}
