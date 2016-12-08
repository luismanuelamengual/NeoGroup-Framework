
package org.neogroup.sparks;

import org.neogroup.sparks.web.WebApplication;

public class Main {

    public static void main(String[] args) {

        WebApplication application = new WebApplication();
        application.registerResourcesContext("/resources", "public");
        application.registerComponents();
        application.startServer();
    }
}