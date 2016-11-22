package org.neogroup.websparks.http.contexts;

import com.sun.net.httpserver.HttpExchange;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class HttpContextInstance {

    private final static Map<Long, HttpContextInstance> instances;

    static {
        instances = new HashMap<>();
    }

    public static HttpContextInstance createInstance (HttpExchange exchange) {
        HttpContextInstance instance = new HttpContextInstance(exchange);
        instances.put(Thread.currentThread().getId(), instance);
        return instance;
    }

    public static void destroyInstance () {
        instances.remove(Thread.currentThread().getId());
    }

    public static HttpContextInstance getInstance() {
        return instances.get(Thread.currentThread().getId());
    }

    private HttpExchange exchange;
    private HttpRequest request;
    private HttpResponse response;

    private HttpContextInstance(HttpExchange exchange) {
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
        }
        return response;
    }
}