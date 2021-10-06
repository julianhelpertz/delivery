package de.deluxesoftware.delivery.api.codec;

import java.io.Serializable;

public interface Message extends Serializable {

    void serialize(DeliveryByteBuf buf);

    void deserialize(DeliveryByteBuf buf);

}
