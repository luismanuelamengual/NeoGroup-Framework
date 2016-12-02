
package org.neogroup.websparks;

import org.neogroup.websparks.processors.TestProcessor;

public class Main {

    public static void main(String[] args) {

        WebApplication application = new WebApplication();
        application.registerProcessor(TestProcessor.class);
        application.startServer();
    }
}