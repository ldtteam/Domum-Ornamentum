package com.ldtteam.domumornamentum.util;

import net.minecraft.util.StringRepresentable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Helper class for safely retrieving values from an enum, combines enum name + serialized name
 */
public final class EnumHelper
{
    private EnumHelper() {}

    public static <E extends Enum<E> & StringRepresentable> Map<String, E> createMap(Class<E> enumClass)
    {
        Map<String, E> map = new HashMap<>();

        for (E value : enumClass.getEnumConstants())
        {
            map.put(value.name().toLowerCase(Locale.ROOT), value);
            map.put(value.getSerializedName().toLowerCase(Locale.ROOT), value);
        }

        return map;
    }

    public static <E extends Enum<E>> E fromString(
        Map<String, E> idMap,
        String input,
        E defaultValue)
    {
        if (input == null)
        {
            return defaultValue;
        }

        return idMap.getOrDefault(input.toLowerCase(Locale.ROOT), defaultValue);
    }
}