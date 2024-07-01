package com.ldtteam.domumornamentum.util;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Extended builder with update support
 */
public class DataComponentPatchBuilder extends DataComponentPatch.Builder
{
    public DataComponentPatchBuilder()
    {
        super();
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(final DataComponentType<T> type, final T defaultValue)
    {
        return ((Optional<T>)map.getOrDefault(type, Optional.empty())).orElse(defaultValue);
    }
    
    public <T> DataComponentPatchBuilder update(final DataComponentType<T> type, final T defaultValue, final UnaryOperator<T> updater) {
        set(type, updater.apply(getOrDefault(type, defaultValue)));
        return this;
    }
}
