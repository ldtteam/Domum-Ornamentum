package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum TrapdoorType implements StringRepresentable
{
    BOSS("boss"),
    COFFER("coffer"),
    FULL("full"),
    HORIZONTAL_BARS("horizontal_bars"),
    HORIZONTALLY_SQUIGGLY_STRIPED("horizontally_squiggly_striped"),
    HORIZONTALLY_STRIPED("horizontally_striped"),
    MOULDING("moulding"),
    PORT_MANTEAU("port_manteau"),
    PORTHOLE("porthole"),
    ROUNDEL("roundel"),
    SLOT("slot"),
    VERTICAL_BARS("vertical_bars"),
    VERTICALLY_SQUIGGLY_STRIPED("vertically_squiggly_striped"),
    VERTICALLY_STRIPED("vertically_striped"),
    WAFFLE("waffle"),
    ;



    private final String serializationName;

    TrapdoorType(final String serializationName) {this.serializationName = serializationName;}

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
}
