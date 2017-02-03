
package org.neogroup.sparks.processors;

import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;
import org.neogroup.sparks.processors.web.WebProcessor;
import org.neogroup.sparks.processors.web.WebRoute;
import org.neogroup.sparks.processors.web.WebAction;

@ProcessorComponent
@WebRoute(path="/rama/")
public class HelloWorldProcessor extends WebProcessor {

    @WebAction()
    protected HttpResponse indexAction(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.setBody("Hola mundeli");
        return response;
    }
}