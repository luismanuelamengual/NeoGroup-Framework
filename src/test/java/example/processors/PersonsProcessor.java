package example.processors;

import example.models.Person;
import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;
import org.neogroup.sparks.web.routing.RouteAction;

import java.util.List;

@Route(path="/person/")
public class PersonsProcessor extends WebProcessor {

    @RouteAction(name="test")
    public HttpResponse testAction(HttpRequest request) {

        Person person = new Person();
        person.setId(1);
        person.setName("Ramon");
        person.setLastName("Salgado");
        person.setAge(52);
        createEntity(person);

        List<Person> persons = retrieveEntities(Person.class);
        StringBuilder personsString = new StringBuilder();
        for (Person p : persons) {
            personsString.append(p);
            personsString.append("<br>");
        }

        return createResponse(personsString.toString());
    }



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
