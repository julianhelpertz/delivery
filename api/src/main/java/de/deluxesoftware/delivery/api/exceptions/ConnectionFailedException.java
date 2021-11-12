package de.deluxesoftware.delivery.api.exceptions;

public class ConnectionFailedException extends RuntimeException {

    public ConnectionFailedException(String msg) {
        super(msg);
    }

}
