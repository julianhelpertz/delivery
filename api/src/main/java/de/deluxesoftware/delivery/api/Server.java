package de.deluxesoftware.delivery.api;

import io.netty.channel.group.ChannelGroup;

public interface Server extends Runnable, Connector {

    void end();

    int getPort();

    int getCurrentConnectionCount();

    void broadCastMessage();

    ChannelGroup getChannels();

}
