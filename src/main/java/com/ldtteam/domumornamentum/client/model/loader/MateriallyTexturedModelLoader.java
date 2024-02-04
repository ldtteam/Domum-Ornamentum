package com.ldtteam.domumornamentum.client.model.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ldtteam.domumornamentum.client.model.geometry.MateriallyTexturedGeometry;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MateriallyTexturedModelLoader implements IGeometryLoader<MateriallyTexturedGeometry>
{

    @SubscribeEvent
    public static void onModelRegistry(final ModelEvent.RegisterGeometryLoaders event)
    {
        event.register(new ResourceLocation(Constants.MOD_ID, Constants.MATERIALLY_TEXTURED_MODEL_LOADER), new MateriallyTexturedModelLoader());
    }

    @Override
    public MateriallyTexturedGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        final String parent = jsonObject.get("parent").getAsString();
        final ResourceLocation parentLocation = new ResourceLocation(parent);

        return new MateriallyTexturedGeometry(parentLocation);
    }
}
