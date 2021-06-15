package com.ldtteam.domumornamentum.client.model.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.client.model.geometry.MateriallyTexturedGeometry;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MateriallyTexturedModelLoader implements IModelLoader<MateriallyTexturedGeometry>
{

    @SubscribeEvent
    public static void onModelRegistry(final ModelRegistryEvent event)
    {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(Constants.MATERIALLY_TEXTURED_MODEL_LOADER), new MateriallyTexturedModelLoader());
    }

    @Override
    public void onResourceManagerReload(@NotNull final IResourceManager resourceManager)
    {
        //The models clean up their own inner caches, since they are not static.
    }

    @NotNull
    @Override
    public MateriallyTexturedGeometry read(@NotNull final JsonDeserializationContext deserializationContext, final JsonObject modelContents)
    {
        final String parent = modelContents.get("parent").getAsString();
        final ResourceLocation parentLocation = new ResourceLocation(parent);

        return new MateriallyTexturedGeometry(parentLocation);
    }
}
