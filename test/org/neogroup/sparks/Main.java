
package org.neogroup.sparks;

import org.neogroup.sparks.processors.PepeProcessor;
import org.neogroup.sparks.processors.RamaProcessor;
import org.neogroup.sparks.processors.TestProcessor;
import org.neogroup.sparks.web.WebModule;

public class Main {

    public static void main(String[] args) {

        WebModule pepeModule = new WebModule(1408);
        pepeModule.registerProcessor(PepeProcessor.class);

        WebModule ramaModule = new WebModule(1409);
        pepeModule.registerProcessor(RamaProcessor.class);

        Application application = new Application();
        application.addModule(pepeModule);
        application.addModule(pepeModule);
        application.registerProcessor(TestProcessor.class);
        application.start();
    }
}