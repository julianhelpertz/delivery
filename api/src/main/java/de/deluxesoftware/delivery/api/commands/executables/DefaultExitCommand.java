package de.deluxesoftware.delivery.api.commands.executables;

import de.deluxesoftware.delivery.api.Server;
import de.deluxesoftware.delivery.api.commands.CommandExecutable;

public class DefaultExitCommand implements CommandExecutable {

    @Override
    public boolean execute(Server server, String command) {
        try {
            server.end();
            System.exit(0);
            return true;
        } catch (Exception ignored) {
            System.exit(-1);
            return false;
        }
    }

}
