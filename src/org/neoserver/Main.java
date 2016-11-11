
package org.neoserver;

import org.neoserver.http.HttpRequest;
import org.neoserver.http.HttpResponse;
import org.neoserver.http.HttpServer;
import org.neoserver.http.context.Context;

public class Main {
    
    public static final String TEXT = "hola mundo";
    
    public static void main(String[] args) throws Exception {
        
        HttpServer server = new HttpServer(1409);
        server.addContext(new Context("/test") {
            @Override
            public HttpResponse onContext(HttpRequest request) {
                return new HttpResponse(TEXT.getBytes());
            }
        });
        server.start();
    }
}