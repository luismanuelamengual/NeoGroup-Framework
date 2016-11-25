package org.neogroup.websparks.actions;

import org.neogroup.websparks.Application;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

import java.util.Map;

public abstract class WebAction extends Action {

    protected final HttpRequest request;
    protected final HttpResponse response;

    public WebAction(Application application, HttpRequest request, HttpResponse response) {
        super(application);
        this.request = request;
        this.response = response;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpResponse getResponse() {
        return response;
    }

    protected Map<String,String> getParameters () {
        return request.getParameters();
    }

    protected String getParameter(String name) {
        return request.getParameter(name);
    }

    protected String getMethod () {
        return request.getMethod();
    }

    protected void setResponseCode(int responseCode) {
        response.setResponseCode(responseCode);
    }

    protected void print (String text) {
        response.write(text);
    }

    protected void flush () {
        response.flush();
    }
}