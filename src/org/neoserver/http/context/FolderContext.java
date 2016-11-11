
package org.neoserver.http.context;

import org.neoserver.http.HttpRequest;
import org.neoserver.http.HttpResponse;

public class FolderContext extends Context {

    public FolderContext(String path) {
        super(path);
    }
    
    @Override
    public HttpResponse onContext(HttpRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}