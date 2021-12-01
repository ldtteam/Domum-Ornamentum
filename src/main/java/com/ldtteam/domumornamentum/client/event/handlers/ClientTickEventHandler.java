package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientTickEventHandler
{
    private static final ClientTickEventHandler INSTANCE = new ClientTickEventHandler();

    public static ClientTickEventHandler getInstance()
    {
        return INSTANCE;
    }

    private long clientTicks = 0;
    private long nonePausedTicks = 0;

    private ClientTickEventHandler()
    {
    }

    @SubscribeEvent
    public static void onTickClientTick(final TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START) {
            ClientTickEventHandler.getInstance().onClientTick();
        }
    }

    private void onClientTick()
    {
        clientTicks++;
        if (!Minecraft.getInstance().isPaused()) {
            nonePausedTicks++;
        }
    }

    public long getClientTicks()
    {
        return clientTicks;
    }

    public long getNonePausedTicks()
    {
        return nonePausedTicks;
    }
}
