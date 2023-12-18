package com.ldtteam.domumornamentum.item.interfaces;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

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
    ResourceLocation getGroup();

    /**
     * Get input ids (E.g. Main, Support, Inner, Outer)
     * @return the associated resource location.
     */
    default List<ResourceLocation> getInputIds()
    {
        return ImmutableList.of(new ResourceLocation(Constants.MOD_ID, "onlyone"));
    }
}
