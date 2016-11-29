package org.neogroup.websparks.actions;

import org.neogroup.websparks.Application;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

public class TestAction extends WebAction {

    public TestAction(Application application, HttpRequest request, HttpResponse response) {
        super(application, request, response);
    }

    @Override
    public void execute() {
        setResponseCode(404);
        print ("hola ...<br>");
        print ("mundeli !!!!");
    }
}