package example.processors;

import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.console.processors.ConsoleProcessor;
import org.neogroup.sparks.console.processors.ProcessCommands;
import org.neogroup.sparks.processors.ProcessorException;

@ProcessCommands({"vane", "infour"})
public class VaneProcessor extends ConsoleProcessor {

    @Override
    public Object process(ConsoleCommand command) throws ProcessorException {
        command.getConsole().println ("Sipisssss !!!. El comando entrado es: " + command.getCommandName());
        return null;
    }
}
