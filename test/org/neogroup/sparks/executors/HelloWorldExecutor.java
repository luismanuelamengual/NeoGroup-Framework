package org.neogroup.sparks.executors;

import org.neogroup.sparks.ExecutorComponent;
import org.neogroup.sparks.routing.WebExecutor;
import org.neogroup.sparks.routing.Route;
import org.neogroup.sparks.routing.http.HttpRequest;
import org.neogroup.sparks.routing.http.HttpResponse;

@ExecutorComponent
@Route(path="/rama/")
public class HelloWorldExecutor extends WebExecutor {

    @Override
    protected void onRequest(HttpRequest request, HttpResponse response) {

        response.write(getString("welcome_phrase", request.getParameter("name")));
    }
}