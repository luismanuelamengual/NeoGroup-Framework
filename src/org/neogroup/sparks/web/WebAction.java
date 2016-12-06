package org.neogroup.sparks.web;

import org.neogroup.sparks.Action;
import org.neogroup.sparks.web.http.HttpResponse;
import org.neogroup.sparks.web.http.HttpRequest;

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