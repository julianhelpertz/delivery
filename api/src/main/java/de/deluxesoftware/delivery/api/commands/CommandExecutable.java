package de.deluxesoftware.delivery.api.commands;

import de.deluxesoftware.delivery.api.Server;

public interface CommandExecutable {

    boolean execute(Server server, String command);

}
