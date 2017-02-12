
package example.processors;

import org.neogroup.httpserver.HttpHeader;
import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import example.resources.User;
import org.neogroup.sparks.web.processors.WebProcessor;
import org.neogroup.sparks.web.routing.Route;
import org.neogroup.sparks.web.routing.RouteAction;
import org.neogroup.util.MimeTypes;

import java.util.List;

@Route(path="/users/")
public class UsersProcessor extends WebProcessor {

    @RouteAction(name="createUser")
    public HttpResponse createUserAction(HttpRequest request) {

        User user = new User();
        user.setName(request.getParameter("name"));
        user.setLastName(request.getParameter("lastName"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        createResource(user);

        return showUsersAction(request);
    }

    @RouteAction(name="showUsers")
    public HttpResponse showUsersAction(HttpRequest request) {

        StringBuilder str = new StringBuilder();
        List<User> users = retrieveResources(User.class);
        for (User user : users) {
            str.append("Name: ").append(user.getName());
            str.append("|");
            str.append("LastName: ").append(user.getLastName());
            str.append("|");
            str.append("age: ").append(user.getAge());
            str.append("<br>");
        }

        HttpResponse response = new HttpResponse();
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_HTML);
        response.setBody(str.toString());
        return response;
    }
}
