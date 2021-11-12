package de.deluxesoftware.delivery.codec;

import de.deluxesoftware.delivery.api.codec.DeliveryByteBuf;
import de.deluxesoftware.delivery.api.codec.Message;
import de.deluxesoftware.delivery.api.codec.MessageFactory;
import de.deluxesoftware.delivery.api.exceptions.NoSuchMessageException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class DeliveryMessageDecoder extends ByteToMessageDecoder {

    private final MessageFactory messageFactory;

    public DeliveryMessageDecoder(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            long id = in.readLong();
            Class<? extends Message> clazz = this.messageFactory.getMessageById(id);
            if (clazz == null)
                throw new NoSuchMessageException(id);
            Message message = clazz.getDeclaredConstructor().newInstance();
            message.deserialize(new DeliveryByteBuf(in));
            out.add(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
