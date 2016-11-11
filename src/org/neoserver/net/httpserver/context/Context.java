package org.neoserver.net.httpserver.context;

import org.neoserver.net.httpserver.HttpRequest;
import org.neoserver.net.httpserver.HttpResponse;

public abstract class Context {
    
    private final String path;

    public Context(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
 
    public abstract HttpResponse onContext (HttpRequest request);
}