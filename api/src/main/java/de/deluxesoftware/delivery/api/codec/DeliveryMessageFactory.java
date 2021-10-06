package de.deluxesoftware.delivery.api.codec;

public interface DeliveryMessageFactory {

    int getMessageId(DeliveryMessage message);

    int getMessageIdByClass(Class<? extends DeliveryMessage> clazz);

    void addMessage(DeliveryMessage message);

    void addMessages(DeliveryMessage... message);

    void addMessageAsClass(Class<? extends DeliveryMessage>... messages);

    Class<? extends DeliveryMessage> getMessageById(int messageId);

}
