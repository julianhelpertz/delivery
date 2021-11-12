package de.deluxesoftware.delivery;

import de.deluxesoftware.delivery.api.Server;
import de.deluxesoftware.delivery.api.codec.Message;
import de.deluxesoftware.delivery.api.codec.MessageFactory;
import de.deluxesoftware.delivery.api.commands.CommandHandler;
import de.deluxesoftware.delivery.api.exceptions.PortAlreadyBoundException;
import de.deluxesoftware.delivery.codec.DeliveryMessageEncoder;
import de.deluxesoftware.delivery.codec.DeliveryMessageDecoder;
import de.deluxesoftware.delivery.misc.Utils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryServer implements Server {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    private static final Logger logger = Logger.getLogger("[Delivery-Server]");

    private EventLoopGroup hiveGroup;
    private EventLoopGroup dronesGroup;
    private ChannelGroup channelGroup;

    private CommandHandler commandHandler;
    private MessageFactory messageFactory;
    private int port;

    public DeliveryServer(MessageFactory messageFactory, int port) {
        logger.log(Level.INFO, "[Delivery-Server] Starting initialization ...");
        this.commandHandler = new CommandHandler(this) {};
        this.messageFactory = messageFactory;
        this.port = port;
        logger.log(Level.INFO, "[Delivery-Server] Initialized!");
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "[Delivery-Server] Checking port {0} ...", this.port);
        if (!Utils.checkPortAvailability(this.port))
            throw new PortAlreadyBoundException(String.format("Port %s is already in use", this.port));
        logger.log(Level.INFO, "[Delivery-Server] Port available!");
        logger.log(Level.INFO, "[Delivery-Server] Starting server...");
        this.hiveGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        this.dronesGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        this.channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        try {
            new ServerBootstrap()
                    .group(this.hiveGroup, this.dronesGroup)
                    .channel(
                            (Epoll.isAvailable())
                                    ? EpollServerSocketChannel.class
                                    : NioServerSocketChannel.class
                    )
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new DeliveryMessageEncoder(messageFactory));
                            channel.pipeline().addLast(new DeliveryMessageDecoder(messageFactory));
                            channel.pipeline().addLast(getInboundHandler());
                        }
                    })
                    .bind(this.port)
                    .sync();
        } catch (Exception e) {
            logger.log(Level.INFO, "[Delivery-Server] An error occurred!");
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        logger.log(Level.INFO, "[Delivery-Server] Server is running fine and accepting connections!");
        this.commandHandler.readLineInputFromStream();
    }

    @Override
    public void end() {
        logger.log(Level.INFO, "[Delivery-Server] Disconnecting active channels...");
        this.channelGroup.disconnect();
        Utils.logSuccess(logger);
        logger.log(Level.INFO, "[Delivery-Server] Closing channel group...");
        this.channelGroup.close();
        Utils.logSuccess(logger);
        logger.log(Level.INFO, "[Delivery-Server] Shutting down event loop groups");
        this.hiveGroup.shutdownGracefully();
        this.dronesGroup.shutdownGracefully();
        Utils.logSuccess(logger);
        logger.log(Level.INFO, "[Delivery-Server] Thank you and goodbye :-)");
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void broadCastMessage(Message message) {
        this.channelGroup.forEach(it -> it.writeAndFlush(message));
    }

    @Override
    public Object getEventManager() {
        return null;
    }

    @Override
    public ChannelGroup getChannels() {
        return this.channelGroup;
    }

    @Override
    public MessageFactory getFactory() {
        return this.messageFactory;
    }

    @Override
    public EventLoopGroup getHiveGroup() {
        return this.hiveGroup;
    }

    public EventLoopGroup getDronesGroup() {
        return this.dronesGroup;
    }

    @Override
    public int getCurrentConnectionCount() {
        return this.channelGroup.size();
    }

    @Override
    public SimpleChannelInboundHandler<Message> getInboundHandler() {
        return new SimpleChannelInboundHandler<Message>() {
            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                cause.printStackTrace();
            }

            @Override
            public void channelActive(ChannelHandlerContext ctx) {
                // TODO document why this method is empty
            }

            @Override
            public void channelInactive(ChannelHandlerContext ctx) {
                // TODO document why this method is empty
            }

            @Override
            public void channelRegistered(ChannelHandlerContext ctx) {
                channelGroup.add(ctx.channel());
            }

            @Override
            public void channelUnregistered(ChannelHandlerContext ctx) {
                channelGroup.remove(ctx.channel());
            }

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
                // TODO document why this method is empty
            }
        };
    }
}
