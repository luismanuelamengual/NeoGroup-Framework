
package org.neogroup.net.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import org.neogroup.encoding.MimeType;
import org.neogroup.net.http.context.Context;

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
            public void handle(HttpExchange exchange) {
        
                try {
                    HttpRequest request = createRequest (exchange);
                    HttpResponse response = null;
                    try {
                        response = context.onContext(request);
                    }
                    catch (Throwable ex) {
                        response = context.onError (request, ex);
                        if (response == null) {
                            response = createDefaulErrorResponse(ex);
                        }
                    }
                    sendResponse (exchange, response);
                }
                catch (Throwable ex) {}
            }
        });
    }
    
    protected HttpRequest createRequest (HttpExchange exchange) throws Exception {
        
        List<HttpHeader> headers = new ArrayList<>();
        Headers requestHeaders = exchange.getRequestHeaders();
        for (String name : requestHeaders.keySet()) {
            headers.add(new HttpHeader(name, requestHeaders.get(name)));
        }

        InputStream inputStream = exchange.getRequestBody();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read = inputStream.read();
        while (read != -1) {
            byteArrayOutputStream.write(read);
            read = inputStream.read();
        }
        byte[] requestBytes = byteArrayOutputStream.toByteArray();

        return new HttpRequest(exchange.getRequestMethod(), headers, exchange.getRequestURI(), requestBytes);
    }
    
    protected void sendResponse (HttpExchange exchange, HttpResponse response) throws Exception {
        
        if (!response.getHeaders().isEmpty()) {
            for (HttpHeader header : response.getHeaders()) {
                exchange.getResponseHeaders().add(header.getName(), header.getValue());
            }
        }

        byte[] responseBody = response.getBody();
        exchange.sendResponseHeaders(response.getResponseCode(), responseBody.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBody);
        os.close();
    }
    
    private HttpResponse createDefaulErrorResponse(Throwable throwable) {
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        throwable.printStackTrace(printer);
        byte[] body = out.toByteArray();
        
        HttpResponse response = new HttpResponse();
        response.setResponseCode(HttpResponse.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
        response.addHeader(new HttpHeader(HttpHeader.CONTENT_TYPE, MimeType.TEXT_PLAIN));
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