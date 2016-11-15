
package org.neogroup.websparks.controllers;

import org.neogroup.websparks.Application;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

public class Controller {

    private Application application;    
    
    public Controller(Application application) {
        this.application = application;
    }

    public HttpResponse executeAction(String controllerAction, HttpRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
