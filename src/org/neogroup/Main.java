
package org.neogroup;

import org.neogroup.net.httpserver.HttpHeader;
import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;
import org.neogroup.net.httpserver.HttpServer;
import org.neogroup.net.httpserver.context.Context;
import org.neogroup.net.httpserver.context.FolderContext;

public class Main {
    
    public static final String TEXT = "hola mundo";
    
    public static void main(String[] args) throws Exception {
        
        HttpServer server = new HttpServer(1408);
        server.addContext(new FolderContext("/resources/", "/home/luis/git/TennisFederation/public"));
        server.addContext(new Context("/") {
            @Override
            public HttpResponse onContext(HttpRequest request) {
                
                HttpResponse response = new HttpResponse(TEXT.getBytes());
                response.addHeader(new HttpHeader("Path", request.getPath()));
                return response;
            }
        });
        server.start();
    }
}