package com.ldtteam.domumornamentum;

import com.ldtteam.domumornamentum.network.NetworkChannel;

/**
 * Network singleton.
 */
public class Network
{
    /**
     * The network instance.
     */
    private static final NetworkChannel network = new NetworkChannel("net-channel");

    /**
     * Get the network handler.
     * @return the network handler.
     */
    public static NetworkChannel getNetwork()
    {
        return network;
    }
}
