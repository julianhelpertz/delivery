package de.deluxesoftware.delivery.codec;

import de.deluxesoftware.delivery.api.codec.Message;
import de.deluxesoftware.delivery.api.codec.MessageFactory;
import de.deluxesoftware.delivery.misc.Utils;

import java.util.HashMap;
import java.util.Map;

public class DeliveryMessageFactory implements MessageFactory {

    private final HashMap<Long, Class<? extends Message>> messageMap = new HashMap<>();

    @Override
    public <T extends Message> long getMessageId(T message) {
        return this.getMessageIdByClass(message.getClass());
    }

    @Override
    public <T extends Message> void removeMessage(T message) {
        this.removeMessageByClass(message.getClass());
    }

    @Override
    public void addMessage(Class<? extends Message> message) {
        this.messageMap.putIfAbsent(Utils.cryptAdler32(message.getSimpleName()), message);
    }

    @Override
    public Class<? extends Message> getMessageById(long messageId) {
        return this.messageMap.get(messageId);
    }

    @Override
    public void removeMessageByClass(Class<? extends Message> clazz) {
        for (Map.Entry<Long, Class<? extends Message>> pair : this.messageMap.entrySet())
            this.messageMap.remove(pair.getKey());
    }

    @Override
    public long getMessageIdByClass(Class<? extends Message> clazz) {
        for (Map.Entry<Long, Class<? extends Message>> pair : this.messageMap.entrySet())
            if (pair.getValue().equals(clazz))
                return pair.getKey();
        return -1L;
    }
}
