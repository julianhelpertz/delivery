package de.deluxesoftware.delivery.api.codec;

public interface MessageFactory {

    <T extends Message> long getMessageId(T message);

    <T extends Message> void removeMessage(T message);

    void addMessage(Class<? extends Message> messages);

    Class<? extends Message> getMessageById(long messageId);

    void removeMessageByClass(Class<? extends Message> clazz);

    long getMessageIdByClass(Class<? extends Message> clazz);

}
