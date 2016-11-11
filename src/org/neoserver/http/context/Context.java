package org.neoserver.http.context;

import org.neoserver.http.HttpRequest;
import org.neoserver.http.HttpResponse;

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