
package org.neogroup.websparks.controllers;

import org.neogroup.websparks.Controller;
import org.neogroup.websparks.routing.Route;
import org.neogroup.websparks.routing.RouteAction;
import org.neogroup.websparks.routing.RouteParam;

@Route(path="/test/")
public class TestController extends Controller {
    
    @RouteAction("pipo")
    public void pipoChippolaz (@RouteParam("name")String name) {
 
        write("Name: " + name + "<br>");
        write("Lastname: " + getRequest().getParameter("lastName"));
    }
}