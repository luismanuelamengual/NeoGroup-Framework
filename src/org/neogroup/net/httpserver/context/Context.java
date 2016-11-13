package org.neogroup.net.httpserver.context;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.neogroup.net.httpserver.HttpHeader;
import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;

public abstract class Context {
    
    private final String path;

    public Context(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    
    public abstract HttpResponse onContext (HttpRequest request);
    
    public HttpResponse onError (HttpRequest request, Throwable throwable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        throwable.printStackTrace(printer);
        byte[] body = out.toByteArray();
        
        HttpResponse response = new HttpResponse();
        response.setResponseCode(HttpResponse.RESPONSE_CODE_INTERNAL_SERVER_ERROR);
        response.addHeader(new HttpHeader("Content-type", "text/plain"));
        response.setBody(body);
        return response;
    }
}