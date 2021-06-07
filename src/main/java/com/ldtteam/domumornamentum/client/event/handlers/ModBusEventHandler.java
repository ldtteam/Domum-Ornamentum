package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.block.BlockRenderType;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusEventHandler
{

    @SubscribeEvent
    public static void onFMLClientSetup(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> ScreenManager.registerFactory(
          ModContainerTypes.ARCHITECTS_CUTTER,
          ArchitectsCutterScreen::new
        ));
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.getArchitectsCutterBlock(), RenderType.getCutout());
        });
    }
}
