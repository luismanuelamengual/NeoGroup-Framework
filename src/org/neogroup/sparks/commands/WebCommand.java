
package org.neogroup.sparks.commands;

import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;

public class WebCommand extends Command {

    private HttpRequest request;
    private HttpResponse response;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }
}
