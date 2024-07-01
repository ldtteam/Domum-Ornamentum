package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
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
    public static void onTickClientTick(final ClientTickEvent.Pre event)
    {
        ClientTickEventHandler.getInstance().onClientTick();
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
