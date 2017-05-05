package example.processors;

import example.models.Person;
import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;
import org.neogroup.sparks.web.routing.RouteAction;

@Route(path="/person/")
public class PersonsProcessor extends WebProcessor {

    @RouteAction(name="createPerson")
    public HttpResponse createPersonAction(HttpRequest request) {

        Person person = new Person();
        person.setName(request.getParameter("name"));
        person.setLastName(request.getParameter("lastName"));
        if (request.hasParameter("age")) {
            person.setAge(Integer.parseInt(request.getParameter("age")));
        }
        createEntity(person);
        return createResponse("Person created Ok !!. Id: " + person.getId());
    }
}
