
package org.neogroup;

import org.neogroup.web.http.HttpHeader;
import org.neogroup.web.http.HttpRequest;
import org.neogroup.web.http.HttpResponse;
import org.neogroup.web.http.HttpServer;
import org.neogroup.web.http.context.Context;
import org.neogroup.web.http.context.FolderContext;

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