package org.neogroup.websparks.processors;

import org.neogroup.websparks.ProcessorComponent;
import org.neogroup.websparks.WebProcessor;
import org.neogroup.websparks.WebRoute;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

@ProcessorComponent
@WebRoute(path = "/test/")
public class TestProcessor extends WebProcessor {

    @Override
    protected void onRequest(HttpRequest request, HttpResponse response) {

        response.write("candulichssss !!!");
        response.write("<br>my name is: " + request.getParameter("name"));
    }
}