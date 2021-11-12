package de.deluxesoftware.delivery.api.commands.executables;

import de.deluxesoftware.delivery.api.Server;
import de.deluxesoftware.delivery.api.commands.CommandExecutable;

import java.util.logging.Level;

public class DefaultCountCommand implements CommandExecutable {

    @Override
    public boolean execute(Server server, String command) {
        server.getLogger().log(Level.INFO, "The current connection count is: {0}", server.getCurrentConnectionCount());
        return true;
    }

}
