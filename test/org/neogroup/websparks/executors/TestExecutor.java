package org.neogroup.websparks.executors;

import org.neogroup.websparks.ExecutorComponent;
import org.neogroup.websparks.WebExecutor;
import org.neogroup.websparks.WebRoute;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

@ExecutorComponent
@WebRoute(path = "/test/")
public class TestExecutor extends WebExecutor {

    @Override
    protected void onRequest(HttpRequest request, HttpResponse response) {

        response.write("candulichssss !!!");
        response.write("<br>my name is: " + request.getParameter("name"));
    }
}