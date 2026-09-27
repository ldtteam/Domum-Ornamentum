package com.ldtteam.domumornamentum.client.model.loader;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class MateriallyTexturedModelLoader {

    private MateriallyTexturedModelLoader() {
        throw new IllegalStateException("Utility class");
    }

    @SubscribeEvent
    public static void onRegisterBlockStateModels(final RegisterBlockStateModels event) {
        event.registerModel(Constants.MATERIALLY_TEXTURED_MODEL_LOADER, MateriallyTexturedUnbakedModel.CODEC);
    }
}
