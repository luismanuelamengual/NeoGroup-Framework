
package org.neogroup.websparks;

import org.neogroup.websparks.controllers.TestController;

public class Main {

    public static void main(String[] args) {

        WebApplication application = new WebApplication();
        application.registerController(TestController.class);
        application.startServer();
    }
}