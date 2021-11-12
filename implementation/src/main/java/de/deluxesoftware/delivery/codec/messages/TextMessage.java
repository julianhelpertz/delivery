package de.deluxesoftware.delivery.codec.messages;

import de.deluxesoftware.delivery.api.codec.DeliveryByteBuf;
import de.deluxesoftware.delivery.api.codec.Message;

public class TextMessage implements Message {

    private String payload;

    public TextMessage(String payload) {
        this.payload = payload;
    }

    @Override
    public void serialize(DeliveryByteBuf buf) {
        buf.writeString(payload);
    }

    @Override
    public void deserialize(DeliveryByteBuf buf) {
        this.payload = buf.readString();
    }

    public String getPayload() {
        return this.payload;
    }
}
