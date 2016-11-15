
package org.neogroup;

import org.neogroup.websparks.http.HttpHeader;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.http.contexts.Context;
import org.neogroup.websparks.http.contexts.FolderContext;

public class Main {
    
    public static final String TEXT = "hola mundo";
    
    public static void main(String[] args) throws Exception {
        
        HttpServer server = new HttpServer(1408);
        server.addContext(new FolderContext("/resources/", "/home/luis/git/sitrack-frontend/public"));
        server.addContext(new Context("/") {
            @Override
            public HttpResponse onContext(HttpRequest request) {
                
                HttpResponse response = new HttpResponse(TEXT.getBytes());
                response.addHeader(new HttpHeader("Path", request.getPath()));
                return response;
            }
        });
        server.start();

//        Application app = new Application();
    }
}