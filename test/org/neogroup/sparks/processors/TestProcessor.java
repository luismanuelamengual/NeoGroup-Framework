
package org.neogroup.sparks.processors;

import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;

@WebRoute(path="/test/")
public class TestProcessor extends WebProcessor {

    @WebAction(name="pepe")
    public HttpResponse pepeAction (HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.setBody("Hello world !!");
        return response;
    }
}
