
package org.neogroup.websparks.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.neogroup.websparks.http.contexts.ContextInstance;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpResponse {
    
    public static final String SERVER_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    
    private static DateFormat dateFormatter;
    
    static {
        dateFormatter = new SimpleDateFormat(SERVER_DATE_FORMAT);
    }
    
    private final HttpExchange exchange;
    private int responseCode;
    private byte[] body;
    private boolean headersSent;
    
    public HttpResponse() {
        this(HttpResponseCode.OK);
    }
    
    public HttpResponse(int responseCode) {
        this.exchange = ContextInstance.getInstance().getExchange();
        this.responseCode = responseCode;
        this.headersSent = false;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    
    public void setBody(String body) {
        setBody(body.getBytes());
    }
    
    public void setBody(byte[] body) {
        this.body = body;
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

    public void send () {
        sendHeaders();
        writeContents();
        closeConnection();
    }
    
    public void write (String text) {
        write(text.getBytes());
    }
    
    public void write (byte[] bytes) {
        sendHeaders(0);
        try { exchange.getResponseBody().write(bytes); } catch (IOException ex) {}
    }
    
    public void flush () {
        sendHeaders(0);
        try { exchange.getResponseBody().flush(); } catch (Exception ex) {}
    }

    private void sendHeaders () {
        sendHeaders(body != null? body.length : -1);
    }

    private void sendHeaders (long contentLength) {
        if (!headersSent) {
            addHeader(HttpHeader.DATE, dateFormatter.format(new Date()));
            try { exchange.sendResponseHeaders(responseCode, contentLength); } catch (IOException ex) {}
            headersSent = true;
        }
    }

    private void writeContents () {
        if (body != null) {
            try { exchange.getResponseBody().write(body); } catch (IOException ex) {}
        }
    }

    private void closeConnection () {
        try { exchange.getResponseBody().close(); } catch (Exception ex) {}
    }
}