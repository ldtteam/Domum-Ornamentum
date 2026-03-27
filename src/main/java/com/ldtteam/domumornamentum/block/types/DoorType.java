package com.ldtteam.domumornamentum.block.types;

import com.ldtteam.domumornamentum.util.EnumHelper;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DoorType implements StringRepresentable
{
    FULL("full"),
    PORT_MANTEAU("port_manteau"),
    VERTICALLY_STRIPED("vertically_striped"),
    WAFFLE("waffle");

    private final String serializationName;

    DoorType(final String serializationName) {this.serializationName = serializationName;}

    @Override
    public String getSerializedName()
    {
        return serializationName;
    }

    public String getTranslationKeySuffix() {
        return getSerializedName().replace("_", ".");
    }

    public String getDefaultEnglishTranslation() {
        final String[] parts = getSerializedName().split("_");
        return Arrays.stream(parts)
          .map(StringUtils::capitalize)
          .collect(Collectors.joining(" "));
    }

    private static final Map<String, DoorType> ID_MAP = EnumHelper.createMap(DoorType.class);

    public static DoorType fromString(final String s, final DoorType defaultType)
    {
        return EnumHelper.fromString(ID_MAP, s, defaultType);
    }
}
