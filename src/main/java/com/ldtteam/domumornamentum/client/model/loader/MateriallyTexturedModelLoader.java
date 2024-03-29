package com.ldtteam.domumornamentum.client.model.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ldtteam.domumornamentum.client.model.geometry.MateriallyTexturedGeometry;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.geometry.GeometryLoaderManager;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MateriallyTexturedModelLoader implements IGeometryLoader<MateriallyTexturedGeometry>
{

    @SubscribeEvent
    public static void onModelRegistry(final ModelEvent.RegisterGeometryLoaders event)
    {
        event.register(Constants.MATERIALLY_TEXTURED_MODEL_LOADER, new MateriallyTexturedModelLoader());
    }

    @Override
    public MateriallyTexturedGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        final String parent = jsonObject.get("parent").getAsString();
        final ResourceLocation parentLocation = new ResourceLocation(parent);

        return new MateriallyTexturedGeometry(parentLocation);
    }
}
