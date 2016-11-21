package org.neogroup.websparks.controllers;

import org.neogroup.websparks.Controller;
import org.neogroup.websparks.routing.Route;
import org.neogroup.websparks.routing.RouteAction;

@Route(path="/rama/")
public class RamaController extends Controller {

    @RouteAction
    public void testAction () {
        write("yes rama");
    }
}