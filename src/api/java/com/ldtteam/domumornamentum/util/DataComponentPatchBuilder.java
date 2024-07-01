package com.ldtteam.domumornamentum.util;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import java.util.Optional;
import java.util.function.Supplier;
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

    public <T> T getOrDefault(final Supplier<DataComponentType<T>> type, final T defaultValue)
    {
        return getOrDefault(type.get(), defaultValue);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(final DataComponentType<T> type, final T defaultValue)
    {
        return ((Optional<T>) map.getOrDefault(type, Optional.empty())).orElse(defaultValue);
    }

    public <T> DataComponentPatchBuilder update(final Supplier<DataComponentType<T>> type, final T defaultValue, final UnaryOperator<T> updater)
    {
        return update(type.get(), defaultValue, updater);
    }

    public <T> DataComponentPatchBuilder update(final DataComponentType<T> type, final T defaultValue, final UnaryOperator<T> updater)
    {
        set(type, updater.apply(getOrDefault(type, defaultValue)));
        return this;
    }

    public <T> DataComponentPatchBuilder set(final Supplier<DataComponentType<T>> type, final T value)
    {
        set(type.get(), value);
        return this;
    }
}
