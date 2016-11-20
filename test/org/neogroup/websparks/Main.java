
package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.http.contexts.HttpControllersContext;
import org.neogroup.websparks.http.contexts.HttpFolderContext;
import org.neogroup.websparks.controllers.RootController;
import org.neogroup.websparks.controllers.TestController;

public class Main {

    public static void main(String[] args) {
 
        HttpControllersContext controllersContext = new HttpControllersContext();
        controllersContext.registerController(TestController.class);
        controllersContext.registerController(RootController.class);

        HttpServer server = new HttpServer(1408);
        server.addContext(controllersContext);
        server.addContext(new HttpFolderContext("/resources/", "/home/luis/git/sitrackfrontend/public"));
        server.start();
    }
}