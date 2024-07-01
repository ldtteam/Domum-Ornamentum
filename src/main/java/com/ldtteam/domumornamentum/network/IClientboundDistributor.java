package com.ldtteam.domumornamentum.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
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
        PacketDistributor.sendToPlayer(player, this);
    }

    public default void sendToDimension(final ServerLevel serverLevel)
    {
        PacketDistributor.sendToPlayersInDimension(serverLevel, this);
    }

    public default void sendToTargetPoint(final ServerLevel level,
        @Nullable final ServerPlayer excluded,
        final double x,
        final double y,
        final double z,
        final double radius)
    {
        PacketDistributor.sendToPlayersNear(level, excluded, x, y, z, radius, this);
    }

    public default void sendToAllClients()
    {
        PacketDistributor.sendToAllPlayers(this);
    }

    public default void sendToTrackingEntity(final Entity entity)
    {
        PacketDistributor.sendToPlayersTrackingEntity(entity, this);
    }

    public default void sendToTrackingEntityAndSelf(final Entity entity)
    {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, this);
    }

    public default void sendToPlayersTrackingChunk(final LevelChunk chunk)
    {
        if (chunk.getLevel() instanceof final ServerLevel level)
        {
            PacketDistributor.sendToPlayersTrackingChunk(level, chunk.getPos(), this);
            return;
        }

        final String crash =
            "Got client chunk for server network message: " + this.getClass().getName() + " - " + chunk.getClass().getName();
        if (FMLEnvironment.production)
        {
            new IllegalArgumentException(crash).printStackTrace();
        }
        else
        {
            throw new IllegalArgumentException(crash);
        }
    }

    public default void sendToPlayersTrackingChunk(final ServerLevel serverLevel, final ChunkPos chunkPos)
    {
        PacketDistributor.sendToPlayersTrackingChunk(serverLevel, chunkPos, this);
    }
}