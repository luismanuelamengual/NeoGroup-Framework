
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

        Template template = getApplicationContext().getTemplateManager().createTemplate(request.getParameter("tpl"));
        template.setParameter("name", request.getParameter("name"));

        HttpResponse response = new HttpResponse();
        response.setBody(template.render());
        return response;
    }
}
