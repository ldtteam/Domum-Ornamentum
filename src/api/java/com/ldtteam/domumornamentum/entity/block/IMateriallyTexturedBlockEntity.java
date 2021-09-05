package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import org.jetbrains.annotations.NotNull;

/**
 * Interface which is applied to the block entity of a materially textured block.
 * Gives access to the material texture data that the block entity contains for the model.
 */
public interface IMateriallyTexturedBlockEntity
{
    /**
     * Replaces the stored material texture data with the one given and then updates the model data.
     * @param materialTextureData The new material texture data to store.
     */
    void updateTextureDataWith(MaterialTextureData materialTextureData);

    /**
     * Gives access to the stored material texture data.
     * @return The stored material texture data.
     */
    @NotNull
    MaterialTextureData getTextureData();
}
