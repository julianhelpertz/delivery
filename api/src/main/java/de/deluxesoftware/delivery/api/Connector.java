package de.deluxesoftware.delivery.api;

import de.deluxesoftware.delivery.api.codec.Message;
import de.deluxesoftware.delivery.api.codec.MessageFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

public interface Connector {

    Logger getLogger();

    Object getEventManager();

    MessageFactory getFactory();

    EventLoopGroup getHiveGroup();

    EventLoopGroup getDronesGroup();

    SimpleChannelInboundHandler<Message> getInboundHandler();

}
