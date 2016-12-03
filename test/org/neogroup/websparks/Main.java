
package org.neogroup.websparks;

public class Main {

    public static void main(String[] args) {

        WebApplication application = new WebApplication();
        application.registerComponents();
        application.startServer();
    }
}