
package org.neogroup.sparks;

import org.neogroup.httpserver.HttpResourcesContext;
import org.neogroup.sparks.processors.TestProcessor;
import org.neogroup.sparks.web.WebModule;

public class Main {

    public static void main(String[] args) {

        WebModule webModule = new WebModule(1408);
        webModule.addContext(new HttpResourcesContext("/resources/", "/home/luis/git/sitracksite/public/"));

        Application application = new Application();
        application.addModule(webModule);
        application.registerProcessor(TestProcessor.class);
        application.start();
    }
}