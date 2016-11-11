
package org.neoserver.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import org.neoserver.http.context.Context;

public class HttpServer {

    private final com.sun.net.httpserver.HttpServer server;
    
    public HttpServer(int port) throws IOException {
        this(port, 0);
    }
    
    public HttpServer(int port, int maxThreads) throws IOException {
        server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(port), 0);
        if (maxThreads > 0) {
            server.setExecutor(Executors.newFixedThreadPool(maxThreads));
        }
        else {
            server.setExecutor(Executors.newCachedThreadPool());
        }
    }
    
    public void addContext (Context context) {
        
        server.createContext(context.getPath(), new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
        
                List<HttpHeader> headers = new ArrayList<>();
                Headers requestHeaders = exchange.getRequestHeaders();
                for (String name : requestHeaders.keySet()) {
                    headers.add(new HttpHeader(name, requestHeaders.get(name)));
                }
                
                InputStream inputStream = exchange.getRequestBody();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    int read = inputStream.read();
                    while (read != -1) {
                        byteArrayOutputStream.write(read);
                        read = inputStream.read();
                    }
                } catch (IOException e) {}
                byte[] requestBytes = byteArrayOutputStream.toByteArray();
                
                HttpRequest request = new HttpRequest(exchange.getRequestMethod(), headers, exchange.getRequestURI(), requestBytes);
                
                HttpResponse response = context.onContext(request);
                
                byte[] responseBody = response.getBody();
                exchange.sendResponseHeaders(response.getResponseCode(), responseBody.length);
                OutputStream os = exchange.getResponseBody();
                os.write(responseBody);
                os.close();
            }
        });
    }
    
    public void start () {
        server.start();
    }
    
    public void stop () {
        server.stop(0);
    }
}