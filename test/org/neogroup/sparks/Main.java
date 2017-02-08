
package org.neogroup.sparks;

import org.neogroup.sparks.processors.*;
import org.neogroup.sparks.web.WebModule;

public class Main {

    public static void main(String[] args) {

        WebModule pepeModule = new WebModule(1408);
        pepeModule.registerProcessor(PepeProcessor.class);

        WebModule ramaModule = new WebModule(1409);
        ramaModule.registerProcessor(RamaProcessor.class);

        Application application = new Application();
        application.addModule(pepeModule);
        application.addModule(ramaModule);
        application.registerProcessor(TestProcessor.class);
        application.registerProcessor(UserResourceProcessor.class);
        application.registerProcessor(UsersProcessor.class);
        application.start();
    }
}