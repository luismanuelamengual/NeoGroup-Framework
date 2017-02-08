
package org.neogroup.sparks;

import org.neogroup.sparks.processors.PepeProcessor;
import org.neogroup.sparks.processors.RamaProcessor;
import org.neogroup.sparks.processors.TestProcessor;
import org.neogroup.sparks.web.WebModule;

public class Main {

    public static void main(String[] args) {

        WebModule pepeModule = new WebModule(1408);
        pepeModule.registerProcessor(PepeProcessor.class);
        pepeModule.registerProcessor(TestProcessor.class);

        WebModule ramaModule = new WebModule(1409);
        ramaModule.registerProcessor(RamaProcessor.class);
        ramaModule.registerProcessor(TestProcessor.class);

        Application application = new Application();
        application.addModule(pepeModule);
        application.addModule(ramaModule);
        application.start();
    }
}