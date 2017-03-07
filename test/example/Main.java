
package example;

import example.processors.*;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.console.ConsoleModule;
import org.neogroup.sparks.web.WebModule;

public class Main {

    public static void main(String[] args) {

        Application application = new Application();

        WebModule pepeModule = new WebModule(application, 1408);
        pepeModule.registerProcessor(PepeProcessor.class);
        application.addModule(pepeModule);

        WebModule ramaModule = new WebModule(application, 1409);
        ramaModule.registerProcessor(RamaProcessor.class);
        application.addModule(ramaModule);

        ConsoleModule consoleModule = new ConsoleModule(application);
        application.addModule(consoleModule);

        application.registerProcessor(TestProcessor.class);
        application.registerProcessor(UserResourceProcessor.class);
        application.registerProcessor(UsersProcessor.class);
        application.start();
    }
}