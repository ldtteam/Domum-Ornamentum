package com.ldtteam.domumornamentum.block.types;

import com.ldtteam.domumornamentum.util.EnumHelper;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FancyDoorType implements StringRepresentable
{
    FULL("full"),
    CREEPER("creeper");

    private final String serializationName;

    FancyDoorType(final String serializationName) {this.serializationName = serializationName;}

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

    private static final Map<String, FancyDoorType> ID_MAP = EnumHelper.createMap(FancyDoorType.class);

    public static FancyDoorType fromString(final String s, final FancyDoorType defaultType)
    {
        return EnumHelper.fromString(ID_MAP, s, defaultType);
    }
}
