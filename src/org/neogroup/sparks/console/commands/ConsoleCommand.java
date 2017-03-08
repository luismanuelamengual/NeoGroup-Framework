
package org.neogroup.sparks.console.commands;

import org.neogroup.sparks.console.Console;
import org.neogroup.sparks.console.Command;

public class ConsoleCommand extends org.neogroup.sparks.commands.Command {

    private final Console console;
    private final Command command;

    public ConsoleCommand(Console console, Command command) {
        this.console = console;
        this.command = command;
    }

    public Console getConsole() {
        return console;
    }

    public Command getCommand() {
        return command;
    }
}
