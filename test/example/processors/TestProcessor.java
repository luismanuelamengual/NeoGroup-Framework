
package example.processors;

import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;
import org.neogroup.sparks.web.routing.RouteAction;

@Route(path="/test/")
public class TestProcessor extends WebProcessor {

    @RouteAction
    public HttpResponse indexAction (HttpRequest request) {
        return createResponse("TEST CONTROLLER !!");
    }

    @RouteAction (name="template")
    public HttpResponse templateAction (HttpRequest request) {
        TemplateHttpResponse response  = createTemplateResponse("templates.saranga");
        response.setParameter("name", request.getParameter("name"));
        return response;
    }

    @RouteAction (name="properties")
    public HttpResponse propertiesAction (HttpRequest request) {
        String property = request.getParameter("property");
        String value = getProperties().get(property);
        return createResponse("El valor de la propiedad \"" + property + "\" es: " + value);
    }
}
