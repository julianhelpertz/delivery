package de.deluxesoftware.delivery.api.exceptions;

public class AlreadyConnectedException extends RuntimeException {

    public AlreadyConnectedException(String msg) {
        super(msg);
    }

}
