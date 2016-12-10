
package org.neogroup.sparks.web.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.neogroup.sparks.util.MimeTypes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

public class HttpResponse {

    private final HttpExchange exchange;
    private int responseCode;
    private ByteArrayOutputStream body;
    private boolean headersSent;

    public HttpResponse(HttpExchange exchange) {
        this.exchange = exchange;
        this.responseCode = HttpResponseCode.OK;
        this.body = new ByteArrayOutputStream();
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
        this.body.reset();
        try { this.body.write(body); } catch (Exception ex) {}
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

    public void write (String text, Object... args) {
        write(MessageFormat.format(text, args).getBytes());
    }
    
    public void write (byte[] bytes) {
        try { this.body.write(bytes); } catch (Exception ex) {}
    }
    
    public void flush () {
        sendHeaders(0);
        writeContents();
    }

    private void sendHeaders () {
        sendHeaders(body.size() > 0? body.size() : -1);
    }

    private void sendHeaders (long contentLength) {
        if (!headersSent) {
            if (!getHeaders().containsKey(HttpHeader.CONTENT_TYPE)) {
                addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_HTML);
            }
            addHeader(HttpHeader.DATE, HttpServerUtils.formatDate(new Date()));
            addHeader(HttpHeader.SERVER, HttpServer.SERVER_NAME);
            try { exchange.sendResponseHeaders(responseCode, contentLength); } catch (IOException ex) {}
            headersSent = true;
        }
    }

    private void writeContents () {
        try { body.writeTo(exchange.getResponseBody()); } catch (IOException ex) {}
        try { exchange.getResponseBody().flush(); } catch (Exception ex) {}
        body.reset();
    }

    private void closeConnection () {
        try { exchange.getResponseBody().close(); } catch (Exception ex) {}
        try { body.close(); } catch (Exception ex) {}
        exchange.close();
    }
}