package com.ldtteam.domumornamentum.block.types;

import com.ldtteam.domumornamentum.util.EnumHelper;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FancyTrapdoorType implements StringRepresentable
{
    FULL("full"),
    CREEPER("creeper");

    private final String serializationName;

    FancyTrapdoorType(final String serializationName) {this.serializationName = serializationName;}

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

    private static final Map<String, FancyTrapdoorType> ID_MAP = EnumHelper.createMap(FancyTrapdoorType.class);

    public static FancyTrapdoorType fromString(final String s, final FancyTrapdoorType defaultType)
    {
        return EnumHelper.fromString(ID_MAP, s, defaultType);
    }
}
