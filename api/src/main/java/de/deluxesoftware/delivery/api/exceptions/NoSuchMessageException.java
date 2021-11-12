package de.deluxesoftware.delivery.api.exceptions;

public class NoSuchMessageException extends RuntimeException {

    public NoSuchMessageException(String msg) {
        super(msg);
    }

    public NoSuchMessageException(long id) {
        super("The given id does not match with a message: " + id);
    }

}
