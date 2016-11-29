
package org.neogroup.websparks;

import org.neogroup.websparks.actions.WebAction;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpResponseCode;
import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.http.contexts.Context;
import org.neogroup.websparks.http.contexts.FolderContext;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Application {

    private final HttpServer server;
    private final Map<String, Class<? extends WebAction>> routes;

    public Application() {
        this.routes = new HashMap<>();

        this.server = new HttpServer(1408);
        this.server.addContext(new Context("/") {
            @Override
            public void onContext(HttpRequest request, HttpResponse response) {

                Class<? extends WebAction> actionClass = resolveRoute(request.getPath());
                if (actionClass != null) {
                    try {
                        Constructor<? extends WebAction> constructor = actionClass.getDeclaredConstructor(Application.class, HttpRequest.class, HttpResponse.class);
                        WebAction action = constructor.newInstance(Application.this, request, response);
                        action.execute();
                    }
                    catch (Throwable ex) {
                        throw new RuntimeException("Error executing action !!");
                    }
                }
                else {
                    onRouteNotFound (request, response);
                }
            }
        });
    }

    protected void onRouteNotFound (HttpRequest request, HttpResponse response) {
        response.setResponseCode(HttpResponseCode.NOT_FOUND);
        response.setBody("Path \"" + request.getPath() + "\" not found !!");
    }

    public void registerResourcesContext (String contextPath, String folder) {
        this.server.addContext(new FolderContext(contextPath, folder));
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