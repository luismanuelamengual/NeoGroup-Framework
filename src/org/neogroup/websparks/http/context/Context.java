package org.neogroup.websparks.http.context;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.neogroup.websparks.encoding.MimeType;
import org.neogroup.websparks.http.HttpHeader;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

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
        response.addHeader(new HttpHeader(HttpHeader.CONTENT_TYPE, MimeType.TEXT_PLAIN));
        response.setBody(body);
        return response;
    }
}