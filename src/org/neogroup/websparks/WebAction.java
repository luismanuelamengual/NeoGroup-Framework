package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

public class WebAction extends Action {

    private final HttpRequest request;
    private final HttpResponse response;

    public WebAction(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpResponse getResponse() {
        return response;
    }
}