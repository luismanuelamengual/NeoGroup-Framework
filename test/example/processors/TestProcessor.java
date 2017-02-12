
package example.processors;

import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.routing.RouteAction;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;

@Route(path="/test/")
public class TestProcessor extends WebProcessor {

    @RouteAction
    public HttpResponse indexAction (HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.setBody("TEST CONTROLLER !!");
        return response;
    }
}
