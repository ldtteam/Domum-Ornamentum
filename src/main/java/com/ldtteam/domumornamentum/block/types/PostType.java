package com.ldtteam.domumornamentum.block.types;

import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Enum list of Post type names
 *
 */
public enum PostType implements StringRepresentable
{
    PLAIN("plain"),
    HEAVY("heavy"),
    TURNED("turned"),
    PINCHED("pinched"),
    DOUBLE("double"),
    QUAD("quad");


    private final String serializationName;

    PostType(final String serializationName) {this.serializationName = serializationName;}

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
