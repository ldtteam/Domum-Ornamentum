package com.ldtteam.domumornamentum.network.messages;


import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

/**
 * Interface for all network messages
 */
public interface IMessage
{
    /**
     * Writes message data to buffer.
     *
     * @param buf network data byte buffer
     */
    void toBytes(final FriendlyByteBuf buf);

    /**
     * Which sides is message able to be executed at.
     *
     * @return CLIENT or SERVER or null (for both)
     */
    @Nullable
    LogicalSide getExecutionSide();

    /**
     * Executes message action.
     *
     * @param ctxIn           network context of incoming message
     * @param isLogicalServer whether message arrived at logical server side
     */
    void onExecute(final NetworkEvent.Context ctxIn, final boolean isLogicalServer);
}
