
package org.neogroup.sparks.console;

import org.neogroup.sparks.Application;
import org.neogroup.sparks.Module;
import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.console.processors.ConsoleSelectorProcessor;
import org.neogroup.sparks.processors.ProcessorNotFoundException;

public class ConsoleModule extends Module {

    private final LocalConsoleHandler consoleHandler;

    public ConsoleModule(Application application) {
        super(application);
        consoleHandler = new LocalConsoleHandler();
        registerProcessor(ConsoleSelectorProcessor.class);
    }

    @Override
    protected void onStart() {
        if (!consoleHandler.isRunning()) {
            Thread consoleThread = new Thread(consoleHandler);
            consoleThread.start();
        }
    }

    @Override
    protected void onStop() {
        if (consoleHandler.isRunning()) {
            consoleHandler.close();
        }
    }

    public class LocalConsoleHandler extends ConsoleHandler {

        public LocalConsoleHandler() {
            super(new Console(System.in, System.out));
        }

        @Override
        protected void onCommandEntered(Console console, Command command) {

            ConsoleCommand consoleCommand = new ConsoleCommand(console, command);
            try {
                processCommand(consoleCommand);
            }
            catch (ProcessorNotFoundException exception) {
                onCommandNotFound(console, command);
            }
            catch (Throwable throwable) {
                onCommandError(console, command, throwable);
            }
        }
    }

    protected void onCommandNotFound (Console console, Command command) {
        console.println("Command \"" + command + "\" not found !!");
    }

    protected void onCommandError (Console console, Command command, Throwable throwable) {
        console.println("Error: " + throwable.getMessage());
    }
}
