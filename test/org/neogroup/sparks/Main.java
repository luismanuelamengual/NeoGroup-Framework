
package org.neogroup.sparks;

import org.neogroup.sparks.executors.HelloWorldExecutor;
import org.neogroup.sparks.routing.WebApplication;

public class Main {

    public static void main(String[] args) {

        WebApplication application = new WebApplication();
        application.registerResourcesContext("/resources", "public");
        //application.registerComponents();
        application.registerExecutor("/test/", HelloWorldExecutor.class);
        application.startServer();
    }
}