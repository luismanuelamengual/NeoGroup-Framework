
package org.neogroup.websparks.http.contexts;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import org.neogroup.websparks.util.MimeTypes;
import org.neogroup.websparks.http.HttpHeader;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpResponseCode;

public abstract class HttpContext {

    private final static Map<Long, HttpExchange> instances;

    static {
        instances = new HashMap<>();
    }

    public static void setCurrentExchange (HttpExchange exchange) {
        long currentThreadId = Thread.currentThread().getId();
        if (exchange != null) {
            instances.put(currentThreadId, exchange);
        }
        else {
            instances.remove(currentThreadId);
        }
    }

    public static HttpExchange getCurrentExchange () {
        return instances.get(Thread.currentThread().getId());
    }

    private final String path;

    public HttpContext(String path) {
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
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
        response.setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
        response.setBody(body);
        return response;
    }
}