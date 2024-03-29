package de.deluxesoftware.delivery;

import de.deluxesoftware.delivery.api.Server;
import de.deluxesoftware.delivery.api.codec.MessageFactory;
import de.deluxesoftware.delivery.codec.DeliveryMessageFactory;
import de.deluxesoftware.delivery.codec.messages.PingMessage;
import de.deluxesoftware.delivery.codec.messages.PongMessage;

public class MockUpMain {

    public static void main(String[] args) {
        MessageFactory factory = new DeliveryMessageFactory();
        factory.addMessage(PingMessage.class);
        factory.addMessage(PongMessage.class);
        Server server = new DeliveryServer(factory, 1337);
        Thread thread = new Thread(server);
        thread.start();
    }

}
