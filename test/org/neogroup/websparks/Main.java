
package org.neogroup.websparks;

import org.neogroup.websparks.executors.TestExecutor;

public class Main {

    public static void main(String[] args) {

        WebApplication application = new WebApplication();
        application.registerExecutor(TestExecutor.class);
        application.startServer();
    }
}