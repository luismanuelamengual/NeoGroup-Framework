
package example.processors;

import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;
import org.neogroup.sparks.web.routing.RouteAction;

import java.util.Locale;

@Route(path="/test/")
public class TestProcessor extends WebProcessor {

    @RouteAction
    public HttpResponse indexAction (HttpRequest request) {
        return createResponse("TEST CONTROLLER !!");
    }

    @RouteAction (name="template")
    public HttpResponse templateAction (HttpRequest request) {
        ViewHttpResponse response  = createViewResponse("templates.helloworld");
        response.setParameter("name", request.getParameter("name"));
        return response;
    }

    @RouteAction (name="properties")
    public HttpResponse propertiesAction (HttpRequest request) {
        String property = request.getParameter("property");
        String value = (String)getProperty(property);
        return createResponse("El valor de la propiedad \"" + property + "\" es: " + value);
    }

    @RouteAction (name="bundles")
    public HttpResponse bundlesAction (HttpRequest request) {
        return createResponse(getString(Locale.ENGLISH, "welcome_phrase", "Luis"));
    }
}
