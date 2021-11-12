package de.deluxesoftware.delivery.codec.messages;

import de.deluxesoftware.delivery.api.codec.DeliveryByteBuf;
import de.deluxesoftware.delivery.api.codec.Message;

public class PingMessage implements Message {

    private long currentTimeMillis;

    public PingMessage() { }

    public PingMessage(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void serialize(DeliveryByteBuf buf) {
        buf.getBuf().writeLong(this.currentTimeMillis);
    }

    @Override
    public void deserialize(DeliveryByteBuf buf) {
        this.currentTimeMillis = buf.getBuf().readLong();
    }

    public long getCurrentTimeMillis() {
        return this.currentTimeMillis;
    }
}
