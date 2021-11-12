package de.deluxesoftware.delivery.api.commands.executables;

import de.deluxesoftware.delivery.api.Server;
import de.deluxesoftware.delivery.api.commands.CommandExecutable;

public class DefaultDisconnectCommand implements CommandExecutable {

    @Override
    public boolean execute(Server server, String command) {
        return true;
    }

}
