package de.deluxesoftware.delivery.api.codec;

import java.util.Collection;

public interface MessageFactory {

    int getMessageId(Message message);

    int getMessageIdByClass(Class<? extends Message> clazz);

    void addMessage(Message message);

    void addMessages(Message... message);

    void addMessageAsClass(Collection<Class<? extends Message>> messages);

    Class<? extends Message> getMessageById(int messageId);

}
