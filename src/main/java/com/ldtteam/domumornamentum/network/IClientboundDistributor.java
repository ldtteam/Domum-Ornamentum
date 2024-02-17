package com.ldtteam.domumornamentum.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor.TargetPoint;
import java.util.Collection;

/**
 * List of possible network targets when sending from server to client.
 */
public interface IClientboundDistributor extends CustomPacketPayload
{
    /**
     * @see #sendToPlayer(ServerPlayer)
     */
    public default void sendToPlayer(final Collection<ServerPlayer> players)
    {
        for (final ServerPlayer serverPlayer : players)
        {
            sendToPlayer(serverPlayer);
        }
    }

    public default void sendToPlayer(final ServerPlayer player)
    {
        PacketDistributor.PLAYER.with(player).send(this);
    }

    public default void sendToDimension(final ResourceKey<Level> dimensionKey)
    {
        PacketDistributor.DIMENSION.with(dimensionKey).send(this);
    }

    public default void sendToTargetPoint(final TargetPoint point)
    {
        PacketDistributor.NEAR.with(point).send(this);
    }

    public default void sendToAllClients()
    {
        PacketDistributor.ALL.noArg().send(this);
    }

    public default void sendToTrackingEntity(final Entity entity)
    {
        PacketDistributor.TRACKING_ENTITY.with(entity).send(this);
    }

    public default void sendToTrackingEntityAndSelf(final Entity entity)
    {
        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(entity).send(this);
    }

    public default void sendToPlayersTrackingChunk(final LevelChunk chunk)
    {
        PacketDistributor.TRACKING_CHUNK.with(chunk).send(this);
    }
}