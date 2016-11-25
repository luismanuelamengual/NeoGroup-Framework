
package org.neogroup.websparks;

import org.neogroup.websparks.actions.TestAction;

public class Main {

    public static void main(String[] args) {

        Application application = new Application();
        application.registerRoute("/test/", TestAction.class);
        application.start();
    }
}