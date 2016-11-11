
package org.neoserver;

import org.neoserver.net.httpserver.HttpHeader;
import org.neoserver.net.httpserver.HttpRequest;
import org.neoserver.net.httpserver.HttpResponse;
import org.neoserver.net.httpserver.HttpServer;
import org.neoserver.net.httpserver.context.Context;
import org.neoserver.net.httpserver.context.FolderContext;

public class Main {
    
    public static final String TEXT = "hola mundo";
    
    public static void main(String[] args) throws Exception {
        
        HttpServer server = new HttpServer(1409);
        server.addContext(new FolderContext("resources", "/home/luis/git/sitrack-frontend/public"));
        server.addContext(new Context("/") {
            @Override
            public HttpResponse onContext(HttpRequest request) {
                
                HttpResponse response = new HttpResponse(TEXT.getBytes());
                response.addHeader(new HttpHeader("Path", request.getPath()));
//                response.addHeader(new HttpHeader("Query", request.getQuery()));
//                response.addHeader(new HttpHeader("Method", request.getMethod()));
                return response;
            }
        });
        server.start();
    }
}