
package org.neogroup.sparks.console;

import org.neogroup.sparks.Application;
import org.neogroup.sparks.Module;

public class ConsoleModule extends Module {

    private final LocalConsoleHandler consoleHandler;

    public ConsoleModule(Application application) {
        super(application);
        consoleHandler = new LocalConsoleHandler();
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
        protected void onCommandEntered(Console console, String command) {
            console.println("shessss optus !!");
        }
    }
}
