
package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.http.contexts.ControllersContext;
import org.neogroup.websparks.http.contexts.FolderContext;
import org.neogroup.websparks.controllers.RootController;
import org.neogroup.websparks.controllers.TestController;

public class Main {

    public static void main(String[] args) {
 
        ControllersContext controllersContext = new ControllersContext();
        controllersContext.registerController(TestController.class);
        controllersContext.registerController(RootController.class);

        HttpServer server = new HttpServer(1408);
        server.addContext(controllersContext);
        server.addContext(new FolderContext("/resources/", "/home/luis/git/sitrackfrontend/public"));
        server.start();
    }
}