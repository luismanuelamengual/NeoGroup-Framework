
package org.neogroup.websparks.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpResponse {
    
    public static final String SERVER_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final String SERVER_NAME = "WebSparks";
    
    private static DateFormat dateFormatter;
    
    static {
        dateFormatter = new SimpleDateFormat(SERVER_DATE_FORMAT);
    }
    
    private final HttpExchange exchange;
    private int responseCode;
    private boolean headersSent;
    
    public HttpResponse() {
        this(HttpResponseCode.OK);
    }
    
    public HttpResponse(int responseCode) {
        this.exchange = HttpServer.getCurrentHttpExchange();
        this.responseCode = responseCode;
        this.headersSent = false;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    
    public Headers getHeaders() {
        return exchange.getResponseHeaders();
    }
    
    public void addHeader (String headerName, String headerValue) {
        exchange.getResponseHeaders().add(headerName, headerValue);
    }
    
    public void clearHeaders () {
        exchange.getResponseHeaders().clear();
    }
    
    private void sendHeaders () {
        sendHeaders(-1);
    }
    
    private void sendHeaders (long contentLength) {
        if (!headersSent) {
            addHeader(HttpHeader.SERVER, SERVER_NAME);
            addHeader(HttpHeader.DATE, dateFormatter.format(new Date()));
            try {
                exchange.sendResponseHeaders(responseCode, contentLength);
            } 
            catch (IOException ex) {
                throw new RuntimeException("Error sending http headers", ex);
            }
            headersSent = true;
        }
    }
    
    public void send () {
        sendHeaders();
        try { exchange.getResponseBody().close(); } catch (Exception ex) {}
    }
    
    public void setBody(String body) {
        setBody(body.getBytes());
    }
    
    public void setBody(byte[] body) {
        sendHeaders(body.length);
        try {
            exchange.getResponseBody().write(body);
        } 
        catch (IOException ex) {
            throw new RuntimeException("Error writing http body", ex);
        }
    }
    
    public void write (String text) {
        write(text.getBytes());
    }
    
    public void write (byte[] bytes) {
        sendHeaders(0);
        try {
            exchange.getResponseBody().write(bytes);
        } 
        catch (IOException ex) {
            throw new RuntimeException("Error writing http text", ex);
        }
    }
    
    public void flush () {
        try {
            exchange.getResponseBody().flush();
        } 
        catch (IOException ex) {
            throw new RuntimeException("Error flushing http contents", ex);
        }
    }
}
