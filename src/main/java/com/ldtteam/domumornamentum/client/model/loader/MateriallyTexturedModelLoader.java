package com.ldtteam.domumornamentum.client.model.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

public class MateriallyTexturedModelLoader implements IModelLoader
{
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager)
    {

    }

    @Override
    public IModelGeometry read(final JsonDeserializationContext deserializationContext, final JsonObject modelContents)
    {
        return null;
    }
}
