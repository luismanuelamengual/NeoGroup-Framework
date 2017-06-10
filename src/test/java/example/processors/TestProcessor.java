
package example.processors;

import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Get;

import java.util.Locale;

public class TestProcessor extends WebProcessor {

    @Get("/test/")
    public HttpResponse indexAction (HttpRequest request) {
        return createResponse("TEST CONTROLLER !!");
    }

    @Get("/test/template")
    public HttpResponse templateAction (HttpRequest request) {
        ViewHttpResponse response  = createViewResponse("templates.helloworld");
        response.setParameter("name", request.getParameter("name"));
        return response;
    }

    @Get("/test/properties")
    public HttpResponse propertiesAction (HttpRequest request) {
        String property = request.getParameter("property");
        String value = (String)getProperty(property);
        return createResponse("El valor de la propiedad \"" + property + "\" es: " + value);
    }

    @Get("/test/bundles")
    public HttpResponse bundlesAction (HttpRequest request) {
        return createResponse(getString(Locale.ENGLISH, "welcome_phrase", "Luis"));
    }
}
