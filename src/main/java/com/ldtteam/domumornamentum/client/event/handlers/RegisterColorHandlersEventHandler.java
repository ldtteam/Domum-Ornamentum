package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.client.color.MateriallyTexturedBlockBlockColor;
import com.ldtteam.domumornamentum.client.color.MateriallyTexturedBlockItemColor;
import com.ldtteam.domumornamentum.util.Constants;
import java.util.List;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class RegisterColorHandlersEventHandler {

    @SubscribeEvent
    public static void onRegisterBlockTintSources(RegisterColorHandlersEvent.BlockTintSources event) {
        event.getBlockColors().register(
                List.of(new MateriallyTexturedBlockBlockColor()),
                ModBlocks.getMateriallyTexturableBlocks()
        );
    }
}
