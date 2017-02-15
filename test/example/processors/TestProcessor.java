
package example.processors;

import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.templating.Template;
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

    @RouteAction (name="template")
    public HttpResponse templateAction (HttpRequest request) {

        Template template = getApplicationContext().getTemplatesManager().createTemplate(request.getParameter("tpl"));
        template.setParameter("name", request.getParameter("name"));

        HttpResponse response = new HttpResponse();
        response.setBody(template.render());
        return response;
    }

    @RouteAction (name="properties")
    public HttpResponse propertiesAction (HttpRequest request) {

        String property = request.getParameter("property");
        String value = getApplicationContext().getProperties().get(property);

        HttpResponse response = new HttpResponse();
        response.setBody("El valor de la propiedad \"" + property + "\" es: " + value);
        return response;
    }
}
