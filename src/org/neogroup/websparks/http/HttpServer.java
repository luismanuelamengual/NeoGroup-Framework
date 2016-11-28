
package org.neogroup.websparks.http;

import org.neogroup.websparks.http.contexts.Context;
import org.neogroup.websparks.util.MimeTypes;

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
        
        server.createContext(context.getPath(), exchange -> {

            HttpRequest request = new HttpRequest(exchange);
            HttpResponse response = new HttpResponse(exchange);
            try {
                context.onContext(request, response);
            }
            catch (Throwable ex) {
                try { onError(request, response, ex); } catch (Throwable ex2) {}
            }
            try { response.send(); } catch (Exception ex) {}
        });
    }
    
    public void removeContext (Context context) {
        server.removeContext(context.getPath());
    }
    
    protected void onError(HttpRequest request, HttpResponse response, Throwable throwable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        throwable.printStackTrace(printer);
        byte[] body = out.toByteArray();
        response.setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
        response.setBody(body);
    }
    
    public void start () {
        server.start();
    }
    
    public void stop () {
        server.stop(0);
    }
}