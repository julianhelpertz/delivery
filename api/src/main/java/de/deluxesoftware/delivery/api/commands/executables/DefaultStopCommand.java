package de.deluxesoftware.delivery.api.commands.executables;

import de.deluxesoftware.delivery.api.Server;
import de.deluxesoftware.delivery.api.commands.CommandExecutable;

public class DefaultStopCommand implements CommandExecutable {

    @Override
    public boolean execute(Server server, String command) {
        try {
            server.end();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}
