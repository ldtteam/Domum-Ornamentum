package com.ldtteam.domumornamentum.datagen;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MateriallyTexturedModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T>
{
    public MateriallyTexturedModelBuilder(final T parent,
                                          final ExistingFileHelper existingFileHelper)
    {
        super(new ResourceLocation(Constants.MOD_ID, Constants.MATERIALLY_TEXTURED_MODEL_LOADER), parent, existingFileHelper);
    }
}
