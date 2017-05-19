package example.processors;

import org.neogroup.sparks.console.Command;
import org.neogroup.sparks.console.Console;
import org.neogroup.sparks.console.processors.ConsoleProcessor;
import org.neogroup.sparks.console.processors.ProcessCommands;

@ProcessCommands({"vane", "infour"})
public class VaneProcessor extends ConsoleProcessor {
    @Override
    protected void processCommand(Console console, Command command) {
        console.println ("SipisssssTTT !!!. El comando entrado es: " + command);
    }
}
