
package org.neogroup.sparks.console.commands;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.console.Console;

public class ConsoleCommand extends Command {

    private final Console console;
    private final String command;
    private String commandName;

    public ConsoleCommand(Console console, String command) {
        this.console = console;
        this.command = command;
        parseCommand (command);
    }

    private void parseCommand (String command) {
        String[] commandParts = command.split(" ");
        commandName = commandParts[0];
    }

    public Console getConsole() {
        return console;
    }

    public String getCommandName() {
        return commandName;
    }
}
