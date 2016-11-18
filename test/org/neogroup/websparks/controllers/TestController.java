
package org.neogroup.websparks.controllers;

import org.neogroup.websparks.Controller;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.routing.Route;
import org.neogroup.websparks.routing.RouteAction;
import org.neogroup.websparks.routing.RouteParam;

@Route(path="/test/")
public class TestController extends Controller {
    
    @RouteAction("pipo")
    public HttpResponse pipoChippolaz (@RouteParam("name")String name) {
 
        HttpResponse response = new HttpResponse();
        response.setBody("Hello \"" + name + "\"");
        return response;
    }
}