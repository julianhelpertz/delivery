package de.deluxesoftware.delivery;

import de.deluxesoftware.delivery.api.Client;
import de.deluxesoftware.delivery.api.codec.Message;
import de.deluxesoftware.delivery.api.codec.MessageFactory;
import de.deluxesoftware.delivery.codec.DeliveryMessageEncoder;
import de.deluxesoftware.delivery.codec.DeliveryMessageDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

public class DeliveryClient implements Client {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    private static final Logger LOGGER = Logger.getLogger("[Delivery]");

    private EventLoopGroup hiveGroup;
    private Channel channel;

    private final MessageFactory messageFactory;

    private String address;
    private int port;

    public DeliveryClient(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public void connect(String address, int port) {
        this.hiveGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        this.address = address;
        this.port = port;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(this.hiveGroup)
                    .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new DeliveryMessageEncoder(messageFactory));
                            channel.pipeline().addLast(new DeliveryMessageDecoder(messageFactory));
                            channel.pipeline().addLast(getInboundHandler());
                        }
                    });
            this.channel = bootstrap.connect(address, port).sync().channel();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        this.channel.disconnect();
        this.channel.close();
        this.hiveGroup.shutdownGracefully();
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public String getAddress() {
        return this.address + this.port;
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public Object getEventManager() {
        return null;
    }

    @Override
    public MessageFactory getFactory() {
        return this.messageFactory;
    }

    @Override
    public EventLoopGroup getHiveGroup() {
        return this.hiveGroup;
    }

    @Override
    public void sendMessage(Message message) {
        this.channel.writeAndFlush(message);
    }

    @Override
    public SimpleChannelInboundHandler<Message> getInboundHandler() {
        return new SimpleChannelInboundHandler<Message>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
                // TODO document why this method is empty
            }
        };
    }
}
