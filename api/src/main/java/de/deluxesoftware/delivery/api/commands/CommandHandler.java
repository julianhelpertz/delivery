package de.deluxesoftware.delivery.api.commands;

import de.deluxesoftware.delivery.api.Server;
import de.deluxesoftware.delivery.api.commands.executables.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final HashMap<String, CommandExecutable> commands = new HashMap<>();
    private final Server server;

    public CommandHandler(Server server) {
        this.server = server;
        this.addCommand("start", new DefaultStartCommand());
        this.addCommand("stop", new DefaultStopCommand());
        this.addCommand("restart", new DefaultRestartCommand());
        this.addCommand("exit", new DefaultExitCommand());
        this.addCommand("count", new DefaultCountCommand());
    }

    public void readLineInputFromStream() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (reader != null) {
                String command = reader.readLine();
                if (command.split(" ")[0].equalsIgnoreCase("exit"))
                    reader = null;
                this.getCommandExecutable(command.split(" ")[0]).execute(this.server, command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, CommandExecutable> getCommands() {
        return this.commands;
    }

    public boolean isCommand(String command) {
        return getCommands().containsKey(command.split(" ")[0]);
    }

    public void addCommand(String command, CommandExecutable executable) {
        getCommands().putIfAbsent(command, executable);
    }

    public CommandExecutable getCommandExecutable(String command) {
        return (getCommands().get(command) == null) ? (srv, cmd) -> false : getCommands().get(command);
    }

}
