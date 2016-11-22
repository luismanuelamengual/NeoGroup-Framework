
package org.neogroup.websparks.controllers;

import org.neogroup.websparks.Controller;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.routing.Route;
import org.neogroup.websparks.routing.RouteAction;
import org.neogroup.websparks.routing.RouteParam;

@Route(path="/test/")
public class TestController extends Controller {

    @RouteAction
    public String indexAction() {
        return "Root Action !!";
    }

    @RouteAction("pipo")
    public String pipoAction (@RouteParam("name")String name) {

        StringBuilder response = new StringBuilder();
        response.append("Name: ");
        response.append(name);
        response.append("<br>");
        response.append("LastName: ");
        response.append(getParameter("lastName"));
        return response.toString();
    }

    @RouteAction("tito")
    public void titoAction () {
        setResponseCode(404);
        print("hi Titoch !!");
    }

    @RouteAction("rama")
    public HttpResponse ramaAction () {

        HttpResponse response = new HttpResponse();
        response.write("hello world from ... ");
        response.write("HttpResponse");
        return response;
    }

    @RouteAction("error")
    public void errorAction () {

        int a = 10 / 0;
    }
}
