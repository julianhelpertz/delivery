package de.deluxesoftware.delivery.api;

import de.deluxesoftware.delivery.api.codec.Message;
import io.netty.channel.Channel;

public interface Client extends ConnectionBoilerplate {

    void connect(String address, int port);

    void disconnect();

    String getAddress();

    Channel getChannel();

    void sendMessage(Message message);

}
