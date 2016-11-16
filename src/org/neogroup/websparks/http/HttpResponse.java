
package org.neogroup.websparks.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpResponse {
    
    public static final String SERVER_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final String SERVER_NAME = "WebSparks";
    
    public static final int RESPONSE_CODE_CONTINUE = 100;
    public static final int RESPONSE_CODE_SWITCHING_PROTOCOLS = 101;
    public static final int RESPONSE_CODE_OK = 200;
    public static final int RESPONSE_CODE_CREATED = 201;
    public static final int RESPONSE_CODE_ACCEPTED = 202;
    public static final int RESPONSE_CODE_NON_AUTHORITATIVE_INFOTMATION = 203;
    public static final int RESPONSE_CODE_NO_CONTENT = 204;
    public static final int RESPONSE_CODE_RESET_CONTENT = 205;
    public static final int RESPONSE_CODE_PARTIAL_CONTENT = 206;
    public static final int RESPONSE_CODE_MULTIPLE_CHOICES = 300;
    public static final int RESPONSE_CODE_MOVED_PERMANENTLY = 301;
    public static final int RESPONSE_CODE_FOUND = 302;
    public static final int RESPONSE_CODE_SEE_OTHER = 303;
    public static final int RESPONSE_CODE_NOT_MODIFIED = 304;
    public static final int RESPONSE_CODE_USE_PROXY = 305;
    public static final int RESPONSE_CODE_TEMPORARY_REDIRECT = 307;
    public static final int RESPONSE_CODE_BAD_REQUEST = 400;
    public static final int RESPONSE_CODE_UNAUTHORIZED = 401;
    public static final int RESPONSE_CODE_PAYMENT_REQUIRED = 402;
    public static final int RESPONSE_CODE_FORBIDDEN = 403;
    public static final int RESPONSE_CODE_NOT_FOUND = 404;
    public static final int RESPONSE_CODE_METHOD_NOT_ALLOWED = 405;
    public static final int RESPONSE_CODE_NOT_ACCEPTABLE = 406;
    public static final int RESPONSE_CODE_REQUEST_TIMEOUT = 408;
    public static final int RESPONSE_CODE_CONFLICT = 409;
    public static final int RESPONSE_CODE_GONE = 410;
    public static final int RESPONSE_CODE_LENGTH_REQUIRED = 411;
    public static final int RESPONSE_CODE_PRECONDITION_FAILED = 412;
    public static final int RESPONSE_CODE_REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int RESPONSE_CODE_REQUEST_URI_TOO_LARGE = 414;
    public static final int RESPONSE_CODE_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int RESPONSE_CODE_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int RESPONSE_CODE_EXPECTATION_FAILED = 417;
    public static final int RESPONSE_CODE_INTERNAL_SERVER_ERROR = 500;
    public static final int RESPONSE_CODE_NOT_IMPLEMENTED = 501;
    public static final int RESPONSE_CODE_BAD_GATEWAY = 502;
    public static final int RESPONSE_CODE_SERVICE_UNAVAILABLE = 503;
    public static final int RESPONSE_CODE_GATEWAY_TIMEOUT = 504;
    public static final int RESPONSE_CODE_HTTP_VERSION_NOT_SUPPORTED = 505;
    
    private static DateFormat dateFormatter;
    
    static {
        dateFormatter = new SimpleDateFormat(SERVER_DATE_FORMAT);
    }
    
    private final HttpExchange exchange;
    private int responseCode;
    private boolean headersSent;
    
    public HttpResponse(HttpExchange exchange) {        
        this.exchange = exchange;
        this.responseCode = RESPONSE_CODE_OK;
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
            addHeader("Server", SERVER_NAME);
            addHeader("Date", dateFormatter.format(new Date()));
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
    
    public void writeBody(String body) {
        HttpResponse.this.writeBody(body.getBytes());
    }
    
    public void writeBody(byte[] body) {
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
