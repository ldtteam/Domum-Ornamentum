package com.ldtteam.domumornamentum.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * List of possible network targets when sending from client to server.
 */
public interface IServerboundDistributor extends CustomPacketPayload
{
    public default void sendToServer()
    {
        final Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.getConnection() != null)
        {
            mc.getConnection().send(this);
        }
    }
}
