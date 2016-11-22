
package org.neogroup.websparks;

import org.neogroup.websparks.encoding.MimeType;
import org.neogroup.websparks.http.HttpHeader;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpResponseCode;
import org.neogroup.websparks.http.contexts.HttpContextInstance;

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
            response = HttpContextInstance.getInstance().getResponse();
        }
        return response;
    }

    public HttpResponse onError (String action, HttpRequest request, Throwable throwable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        throwable.printStackTrace(printer);
        byte[] body = out.toByteArray();
        HttpResponse response = new HttpResponse();
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeType.TEXT_PLAIN);
        response.setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
        response.setBody(body);
        return response;
    }

    protected HttpRequest getRequest() {
        return HttpContextInstance.getInstance().getRequest();
    }

    protected HttpResponse getResponse() {
        return HttpContextInstance.getInstance().getResponse();
    }

    protected Map<String,String> getParameters() {
        return getRequest().getParameters();
    }

    protected String getParameter(String name) {
        return getParameters().get(name);
    }

    protected String getMethod () {
        return getRequest().getMethod();
    }

    protected void setResponseCode (int responseCode) {
        getResponse().setResponseCode(responseCode);
    }

    protected void print(byte[] text) {
        getResponse().write(text);
    }

    protected void print(String text) {
        getResponse().write(text);
    }

    protected void flush () {
        getResponse().flush();
    }
}