
package app.tests;

import org.neogroup.sparks.console.Console;
import org.neogroup.sparks.console.processors.ConsoleProcessor;
import org.neogroup.sparks.console.ConsoleCommand;

public class VaneProcessor extends ConsoleProcessor {

    @ConsoleCommand("vane")
    protected void processCommand(Console console, ConsoleCommand command) {
        console.println ("SipisssssTTT !!!. El comando entrado es: " + command);
    }
}
