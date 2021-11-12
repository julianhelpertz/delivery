package de.deluxesoftware.delivery.api;

import de.deluxesoftware.delivery.api.codec.Message;
import io.netty.channel.group.ChannelGroup;

public interface Server extends Runnable, ConnectionBoilerplate {

    void end();

    int getPort();

    int getCurrentConnectionCount();

    void broadCastMessage(Message message);

    ChannelGroup getChannels();

}
