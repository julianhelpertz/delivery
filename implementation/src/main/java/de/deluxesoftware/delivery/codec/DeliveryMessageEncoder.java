package de.deluxesoftware.delivery.codec;

import de.deluxesoftware.delivery.api.codec.DeliveryByteBuf;
import de.deluxesoftware.delivery.api.codec.Message;
import de.deluxesoftware.delivery.api.codec.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DeliveryMessageEncoder extends MessageToByteEncoder<Message> {

    private final MessageFactory messageFactory;

    public DeliveryMessageEncoder(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        out.writeLong(this.messageFactory.getMessageId(msg));
        msg.serialize(new DeliveryByteBuf(out));
    }
}
