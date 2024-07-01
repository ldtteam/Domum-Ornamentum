package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.client.color.MateriallyTexturedBlockBlockColor;
import com.ldtteam.domumornamentum.client.color.MateriallyTexturedBlockItemColor;
import com.ldtteam.domumornamentum.util.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterColorHandlersEventHandler {

    @SubscribeEvent
    public static void onRegisterColorHandlersItem(RegisterColorHandlersEvent.Item event) {
        event.register(
                new MateriallyTexturedBlockItemColor(),
                ModBlocks.getMateriallyTexturableItems()
        );
    }

    @SubscribeEvent
    public static void onRegisterColorHandlersBlock(RegisterColorHandlersEvent.Block event) {
        event.register(
                new MateriallyTexturedBlockBlockColor(),
                ModBlocks.getMateriallyTexturableBlocks()
        );
    }
}
