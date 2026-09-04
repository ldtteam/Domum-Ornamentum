package com.ldtteam.domumornamentum.client.model.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ldtteam.domumornamentum.client.model.geometry.MateriallyTexturedUnbakedModel;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.UnbakedModelLoader;

@EventBusSubscriber(value = Dist.CLIENT)
public class MateriallyTexturedModelLoader implements UnbakedModelLoader<MateriallyTexturedUnbakedModel> {
    @SubscribeEvent
    public static void onRegisterLoaders(final ModelEvent.RegisterLoaders event) {
        event.register(Constants.MATERIALLY_TEXTURED_MODEL_LOADER, new MateriallyTexturedModelLoader());
    }

    @Override
    public MateriallyTexturedUnbakedModel read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
        return new MateriallyTexturedUnbakedModel(Identifier.parse(jsonObject.get("parent").getAsString()));
    }
}
