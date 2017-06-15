
package app.tests;

import org.neogroup.sparks.console.Command;
import org.neogroup.sparks.console.Console;
import org.neogroup.sparks.console.processors.ConsoleProcessor;
import org.neogroup.sparks.console.ConsoleCommand;

public class VaneProcessor extends ConsoleProcessor {

    @ConsoleCommand("vane")
    public void processCommand(Console console, Command command) {
        console.println ("SipisssssTTT !!!. El comando entrado es: " + command);
    }
}
