package com.ldtteam.domumornamentum.datagen;

import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.datagen.model.CustomLoaderBuilder;
import com.ldtteam.domumornamentum.datagen.model.ModelBuilder;

public class MateriallyTexturedModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T>
{
    public MateriallyTexturedModelBuilder(final T parent)
    {
        super(Constants.MATERIALLY_TEXTURED_MODEL_LOADER, parent, false);
    }
}
