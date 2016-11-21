
package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.http.contexts.HttpControllersContext;
import org.neogroup.websparks.http.contexts.HttpFolderContext;

public class Main {

    public static void main(String[] args) {

        HttpServer server = new HttpServer(1408);
        server.addContext(new HttpFolderContext("/resources/", "/home/luis/git/TennisFederation/public"));
        server.addContext(new HttpControllersContext());
        server.start();
    }
}