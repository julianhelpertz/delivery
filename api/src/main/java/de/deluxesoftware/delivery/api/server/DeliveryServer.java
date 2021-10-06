package de.deluxesoftware.delivery.api.server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public interface DeliveryServer extends Runnable {

    void end();

    int getPort();

    int getCurrentConnectionCount();

    void broadCastMessage();

    ChannelGroup getChannels();

    EventLoopGroup getHiveGroup();

    EventLoopGroup getDronesGroup();

    Object getPacketFactory();

    Object getEventManager();

    SimpleChannelInboundHandler<Object> getInboundHandler();

}
