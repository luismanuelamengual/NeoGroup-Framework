package org.neogroup.websparks.controllers;

import org.neogroup.websparks.ControllerComponent;
import org.neogroup.websparks.WebCommand;
import org.neogroup.websparks.WebController;
import org.neogroup.websparks.WebRoute;

@ControllerComponent
@WebRoute(path = "/tata/")
public class TestController extends WebController {

    @Override
    public Object execute(WebCommand command) {

        command.getResponse().write("holanduli");
        return null;
    }
}