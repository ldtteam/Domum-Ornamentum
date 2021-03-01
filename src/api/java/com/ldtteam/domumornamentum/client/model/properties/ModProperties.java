package com.ldtteam.domumornamentum.client.model.properties;

import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import net.minecraftforge.client.model.data.ModelProperty;

public class ModProperties
{

    private ModProperties()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModProperties. This is a utility class");
    }

    public static ModelProperty<MaterialTextureData> MATERIAL_TEXTURE_PROPERTY = new ModelProperty<>();
}
