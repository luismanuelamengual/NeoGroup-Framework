
package org.neogroup.websparks.controllers;

import org.neogroup.websparks.Controller;
import org.neogroup.websparks.routing.Route;
import org.neogroup.websparks.routing.RouteAction;

@Route(path="/")
public class RootController extends Controller {
    
    @RouteAction
    public String indexAction () {

        return "holach";
    }
}