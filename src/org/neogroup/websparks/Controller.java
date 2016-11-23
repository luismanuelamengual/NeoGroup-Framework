
package org.neogroup.websparks;

import org.neogroup.websparks.util.MimeTypes;
import org.neogroup.websparks.http.HttpHeader;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpResponseCode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

public abstract class Controller {

    public HttpResponse onBeforeAction (String action, HttpRequest request) {
        return null;
    }

    public HttpResponse onAfterAction (String action, HttpRequest request, Object actionResponse) {
        HttpResponse response = null;
        if (actionResponse != null) {
            if (actionResponse instanceof HttpResponse) {
                response = (HttpResponse) actionResponse;
            } else {
                response = new HttpResponse();
                response.setBody(actionResponse.toString());
            }
        }
        else {
            response = new HttpResponse();
        }
        return response;
    }

    public HttpResponse onError (String action, HttpRequest request, Throwable throwable) {
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