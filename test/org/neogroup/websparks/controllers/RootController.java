
package org.neogroup.websparks.controllers;

import org.neogroup.websparks.Controller;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.routing.Route;
import org.neogroup.websparks.routing.RouteAction;

@Route(path="/")
public class RootController extends Controller {
    
    @RouteAction
    public HttpResponse indexAction () {
        
        HttpResponse response = new HttpResponse();
        response.setBody("MainAction !!");
        return response;
    }
}
