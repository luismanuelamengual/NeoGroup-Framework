
package org.neogroup.websparks;

import java.io.IOException;
import org.neogroup.websparks.http.HttpServer;

public class Application {
    
    private final HttpServer server;

    public Application () {
        this(80);
    }
    
    public Application(int port) {
        this(port, 0);
    }
    
    public Application(int port, int maxThreads) {
        
        try {
            this.server = new HttpServer(port, maxThreads);
        }
        catch (IOException exception) {
            throw new RuntimeException("Unable to execute web server !!", exception);
        }
    }
}