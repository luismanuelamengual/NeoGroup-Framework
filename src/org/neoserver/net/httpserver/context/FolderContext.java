
package org.neoserver.net.httpserver.context;

import org.neoserver.net.httpserver.HttpRequest;
import org.neoserver.net.httpserver.HttpResponse;

public class FolderContext extends Context {

    public FolderContext(String path) {
        super(path);
    }
    
    @Override
    public HttpResponse onContext(HttpRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}