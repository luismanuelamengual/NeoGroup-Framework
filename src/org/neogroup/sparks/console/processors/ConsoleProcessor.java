
package org.neogroup.sparks.console.processors;

import org.neogroup.sparks.console.Command;
import org.neogroup.sparks.console.Console;
import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorException;

public abstract class ConsoleProcessor extends Processor<ConsoleCommand, Object> {

    @Override
    public Object process(ConsoleCommand command) throws ProcessorException {
        processCommand (command.getConsole(), command.getCommand());
        return null;
    }

    protected abstract void processCommand (Console console, Command command);
}
