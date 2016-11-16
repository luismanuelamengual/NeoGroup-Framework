
package org.neogroup.websparks.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import org.neogroup.websparks.http.contexts.Context;

public class HttpServer {

    private final com.sun.net.httpserver.HttpServer server;
    private static Map<Long, HttpRequest> requests;
    private static Map<Long, HttpResponse> responses;
    
    static {
        requests = new HashMap<>();
        responses = new HashMap<>();
    }
    
    public static HttpRequest getCurrentRequest () {
        return requests.get(Thread.currentThread().getId());
    }
    
    public static HttpResponse getCurrentResponse () {
        return responses.get(Thread.currentThread().getId());
    }
    
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
                
                long currentThreadId = Thread.currentThread().getId();
                HttpRequest request = new HttpRequest(exchange);
                HttpResponse response = new HttpResponse(exchange);
                requests.put(currentThreadId, request);
                responses.put(currentThreadId, response);
                try {    
                    context.onContext(request, response);
                }
                catch (Throwable ex) {
                    try {
                        context.onError (request, response, ex);
                    }
                    catch (Throwable ex2) {
                        onError(request, response, ex);
                    }
                }
                finally {
                    try { response.send(); } catch (Exception ex) {}
                    requests.remove(currentThreadId);
                    responses.remove(currentThreadId);
                }
            }
        });
    }
    
    protected void onError(HttpRequest request,  HttpResponse response, Throwable throwable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        throwable.printStackTrace(printer);
        byte[] body = out.toByteArray();
        
        response.setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
        response.writeBody(body);
    }
    
    public void start () {
        server.start();
    }
    
    public void stop () {
        server.stop(0);
    }
}