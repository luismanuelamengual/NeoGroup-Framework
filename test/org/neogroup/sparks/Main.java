
package org.neogroup.sparks;

import org.neogroup.sparks.processors.TestProcessor;
import org.neogroup.sparks.web.WebModule;

public class Main {

    public static void main(String[] args) {

        Application application = new Application();
        application.addModule(new WebModule(1408));
        application.registerProcessor(TestProcessor.class);
        application.start();
    }
}