
package org.neogroup.sparks.processors;

import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebAction;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.processors.WebRoute;

@WebRoute(path="/test/")
public class TestProcessor extends WebProcessor {

    @WebAction(name="pepe")
    public HttpResponse pepeAction (HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.setBody("Hi " + request.getParameter("name") +  ". Hello world !!");
        return response;
    }
}
