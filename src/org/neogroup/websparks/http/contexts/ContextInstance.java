package org.neogroup.websparks.http.contexts;

import com.sun.net.httpserver.HttpExchange;
import org.neogroup.websparks.encoding.MimeType;
import org.neogroup.websparks.http.HttpHeader;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ContextInstance {

    private final static Map<Long, ContextInstance> instances;

    static {
        instances = new HashMap<>();
    }

    public static ContextInstance createInstance (HttpExchange exchange) {
        ContextInstance instance = new ContextInstance(exchange);
        instances.put(Thread.currentThread().getId(), instance);
        return instance;
    }

    public static void destroyInstance () {
        instances.remove(Thread.currentThread().getId());
    }

    public static ContextInstance getInstance() {
        return instances.get(Thread.currentThread().getId());
    }

    private HttpExchange exchange;
    private HttpRequest request;
    private HttpResponse response;

    private ContextInstance (HttpExchange exchange) {
        this.exchange = exchange;
    }

    public HttpExchange getExchange() {
        return exchange;
    }

    public HttpRequest getRequest() {
        if (request == null) {
            request = new HttpRequest();
        }
        return request;
    }

    public HttpResponse getResponse() {
        if (response == null) {
            response = new HttpResponse();
            response.addHeader(HttpHeader.CONTENT_TYPE, MimeType.TEXT_HTML);
        }
        return response;
    }
}