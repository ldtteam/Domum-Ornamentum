package com.ldtteam.domumornamentum.network;

import com.ldtteam.domumornamentum.network.messages.IMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.ldtteam.domumornamentum.util.Constants.MOD_ID;

/**
 * Simple network channel.
 */
public class NetworkChannel
{
    /**
     * Forge network channel
     */
    private final SimpleChannel rawChannel;

    /**
     * Creates a new instance of network channel.
     *
     * @param channelName unique channel name
     * @throws IllegalArgumentException if channelName already exists
     */
    public NetworkChannel(final String channelName)
    {
        final String modVersion = ModList.get().getModContainerById(MOD_ID).get().getModInfo().getVersion().toString();
        rawChannel = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, channelName), () -> modVersion, str -> str.equals(modVersion), str -> str.equals(modVersion));
    }

    public void registerMessages()
    {
        int idx = 0;

    }

    /**
     * Register a message into rawChannel.
     *
     * @param <MSG>      message class type
     * @param id         network id
     * @param msgClazz   message class
     * @param msgCreator supplier with new instance of msgClazz
     */
    private <MSG extends IMessage> void registerMessage(final int id, final Class<MSG> msgClazz, final Function<FriendlyByteBuf, MSG> msgCreator)
    {
        rawChannel.registerMessage(id, msgClazz, IMessage::toBytes, msgCreator, NetworkChannel::execute);
    }

    /**
     * Execute the received message.
     *
     * @param msg         the message.
     * @param ctxSupplier the context supplier.
     */
    private static void execute(@NotNull final IMessage msg, @NotNull final Supplier<NetworkEvent.Context> ctxSupplier)
    {
        final NetworkEvent.Context ctx = ctxSupplier.get();
        final LogicalSide packetOrigin = ctx.getDirection().getOriginationSide();
        if (msg.getExecutionSide() == null || !packetOrigin.equals(msg.getExecutionSide()))
        {
            ctx.enqueueWork(() -> msg.onExecute(ctx, packetOrigin.equals(LogicalSide.CLIENT)));
        }
    }

    /**
     * Sends to server.
     *
     * @param msg message to send
     */
    public void sendToServer(final IMessage msg)
    {
        rawChannel.sendToServer(msg);
    }

    /**
     * Sends to player.
     *
     * @param msg    message to send
     * @param player target player
     */
    public void sendToPlayer(final IMessage msg, final ServerPlayer player)
    {
        rawChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
