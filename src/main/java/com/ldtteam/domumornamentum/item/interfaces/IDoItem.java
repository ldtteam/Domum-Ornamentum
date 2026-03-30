package com.ldtteam.domumornamentum.item.interfaces;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.Identifier;

import java.util.List;

/**
 * Interface for DO specific items.
 */
public interface IDoItem
{
    /**
     * The group this block belongs to.
     * @return the group.
     */
    Identifier getGroup();

    /**
     * Get input ids (E.g. Main, Support, Inner, Outer)
     * @return the associated resource location.
     */
    default List<Identifier> getInputIds()
    {
        return ImmutableList.of(Constants.resLocDO("onlyone"));
    }

    /**
     * If previews should be rendered.
     * @return true if so.
     */
    default boolean renderPreview()
    {
        return false;
    }
}
