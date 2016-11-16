
package org.neogroup.websparks;

import com.sun.net.httpserver.Headers;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpServer;

public class Controller {

    protected HttpRequest getRequest() {
        return HttpServer.getCurrentRequest();
    }
    
    protected HttpResponse getResponse() {
        return HttpServer.getCurrentResponse();
    }
    
    public int getResponseCode() {
        return getResponse().getResponseCode();
    }

    public void setResponseCode(int responseCode) {
        getResponse().setResponseCode(responseCode);
    }
    
    protected Headers getHeaders() {
        return getResponse().getHeaders();
    }
    
    protected void addHeader (String headerName, String headerValue) {
        getResponse().addHeader(headerName, headerValue);
    }
    
    protected void writeContent(String body) {
        getResponse().writeBody(body);
    }
    
    protected void writeContent(byte[] body) {
        getResponse().writeBody(body);
    }
    
    protected void write (String text) {
        getResponse().write(text);
    }
    
    protected void write (byte[] bytes) {
        getResponse().write(bytes);
    }
    
    protected void flush () {
        getResponse().flush();
    }
}
