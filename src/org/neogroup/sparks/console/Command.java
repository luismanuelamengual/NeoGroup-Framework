
package org.neogroup.sparks.console;

import java.util.Arrays;
import java.util.List;

public class Command {

    private final String command;
    private final String commandName;

    public Command(String command) {
        this.command = command;
        List<String> commandTokens = Arrays.asList(command.split(" "));
        commandName = commandTokens.get(0);
    }

    public String getCommandName() {
        return commandName;
    }

    @Override
    public String toString() {
        return commandName;
    }
}
