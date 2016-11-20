
package org.neogroup.websparks.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.neogroup.websparks.http.contexts.Context;
import org.neogroup.websparks.http.contexts.ContextInstance;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServer {

    private final com.sun.net.httpserver.HttpServer server;

    public HttpServer(int port) {
        this(port, 0);
    }
    
    public HttpServer(int port, int maxThreads) {
        try {
            server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(port), 0);
            if (maxThreads > 0) {
                server.setExecutor(Executors.newFixedThreadPool(maxThreads));
            }
            else {
                server.setExecutor(Executors.newCachedThreadPool());
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Could not create http server", ex);
        }
    }
    
    public void addContext (Context context) {
        
        server.createContext(context.getPath(), new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {

                ContextInstance instance = ContextInstance.createInstance(exchange);
                HttpRequest request = instance.getRequest();
                HttpResponse response = null;
                try {    
                    response = context.onContext(request);
                }
                catch (Throwable ex) {
                    try {
                        response = context.onError (request, ex);
                    }
                    catch (Throwable ex2) {
                        response = onError(request, ex);
                    }
                }
                finally {
                    try { response.send(); } catch (Exception ex) {}
                    ContextInstance.destroyInstance();
                }
            }
        });
    }
    
    public void removeContext (Context context) {
        server.removeContext(context.getPath());
    }
    
    protected HttpResponse onError(HttpRequest request, Throwable throwable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        throwable.printStackTrace(printer);
        byte[] body = out.toByteArray();
        
        HttpResponse response = new HttpResponse(HttpResponseCode.INTERNAL_SERVER_ERROR);
        response.setBody(body);
        return response;
    }
    
    public void start () {
        server.start();
    }
    
    public void stop () {
        server.stop(0);
    }
}