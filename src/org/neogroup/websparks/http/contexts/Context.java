
package org.neogroup.websparks.http.contexts;

import org.neogroup.websparks.http.HttpHeader;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpResponseCode;
import org.neogroup.websparks.util.MimeTypes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public abstract class Context {

    private final String path;

    public Context(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    
    public abstract void onContext (HttpRequest request, HttpResponse response);
    
    public void onError (HttpRequest request, HttpResponse response, Throwable throwable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        throwable.printStackTrace(printer);
        byte[] body = out.toByteArray();
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
        response.setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
        response.setBody(body);
    }
}