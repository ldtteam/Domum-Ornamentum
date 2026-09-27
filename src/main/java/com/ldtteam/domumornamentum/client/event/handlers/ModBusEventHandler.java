package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.util.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ModBusEventHandler
{
    @SubscribeEvent
    public static void onMenuScreensRegistry(final RegisterMenuScreensEvent event)
    {
        event.register(ModContainerTypes.ARCHITECTS_CUTTER.get(), ArchitectsCutterScreen::new);
    }
}