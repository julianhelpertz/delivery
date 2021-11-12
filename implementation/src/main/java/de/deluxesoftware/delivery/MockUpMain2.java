package de.deluxesoftware.delivery;

import de.deluxesoftware.delivery.api.Client;
import de.deluxesoftware.delivery.api.codec.MessageFactory;
import de.deluxesoftware.delivery.codec.DeliveryMessageFactory;
import de.deluxesoftware.delivery.codec.messages.PingMessage;
import de.deluxesoftware.delivery.codec.messages.PongMessage;

public class MockUpMain2 {

    public static void main(String[] args){
        MessageFactory factory = new DeliveryMessageFactory();
        factory.addMessage(PingMessage.class);
        factory.addMessage(PongMessage.class);
        Client client = new DeliveryClient(factory);
        client.connect("127.0.0.1", 1337);
        client.sendMessage(new PingMessage(System.currentTimeMillis()));
    }

}
