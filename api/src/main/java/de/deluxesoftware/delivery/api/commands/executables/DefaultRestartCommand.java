package de.deluxesoftware.delivery.api.commands.executables;

import de.deluxesoftware.delivery.api.Server;
import de.deluxesoftware.delivery.api.commands.CommandExecutable;

public class DefaultRestartCommand implements CommandExecutable {

    @Override
    public boolean execute(Server server, String command) {
        try {
            server.end();
            server.run();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
