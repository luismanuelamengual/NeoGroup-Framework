
package org.neogroup.websparks;

import org.neogroup.websparks.actions.Action;
import org.neogroup.websparks.actions.WebAction;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.http.contexts.HttpContext;
import org.neogroup.websparks.http.contexts.HttpFolderContext;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Application {

    private final HttpServer server;
    private final Map<String, Class<? extends WebAction>> routes;

    public Application() {
        this.routes = new HashMap<>();

        this.server = new HttpServer(1408);
        this.server.addContext(new HttpContext("/") {
            @Override
            public HttpResponse onContext(HttpRequest request) {

                HttpResponse response = new HttpResponse();
                Class<? extends WebAction> actionClass = resolveRoute(request.getPath());
                try {

                    Constructor<? extends WebAction> constructor = actionClass.getDeclaredConstructor(Application.class, HttpRequest.class, HttpResponse.class);
                    WebAction action = constructor.newInstance(Application.this, request, response);
                    action.execute();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return response;
            }
        });
    }

    public void registerResourcesContext (String contextPath, String folder) {
        this.server.addContext(new HttpFolderContext(contextPath, folder));
    }

    public void registerRoute (String route, Class<? extends WebAction> action) {
        routes.put(route, action);
    }

    private Class<? extends WebAction> resolveRoute (String route) {
        return routes.get(route);
    }

    public void start () {
        server.start();
    }

    public void stop () {
        server.stop();
    }
}