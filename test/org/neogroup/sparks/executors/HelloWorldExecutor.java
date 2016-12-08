package org.neogroup.sparks.executors;

import org.neogroup.sparks.ExecutorComponent;
import org.neogroup.sparks.web.WebExecutor;
import org.neogroup.sparks.web.WebRoute;
import org.neogroup.sparks.web.http.HttpRequest;
import org.neogroup.sparks.web.http.HttpResponse;

@ExecutorComponent
@WebRoute(path="/rama/")
public class HelloWorldExecutor extends WebExecutor {

    @Override
    protected void onRequest(HttpRequest request, HttpResponse response) {

        response.write(getString("welcome_phrase", request.getParameter("name")));
    }
}