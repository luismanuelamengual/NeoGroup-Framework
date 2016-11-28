
package org.neogroup.websparks;

import org.neogroup.websparks.actions.WebAction;
import org.neogroup.websparks.http.*;
import org.neogroup.websparks.http.contexts.Context;
import org.neogroup.websparks.http.contexts.FolderContext;
import org.neogroup.websparks.util.MimeTypes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Application {

    public static final String APP_NAME = "WebSparks";

    private final HttpServer server;
    private final Map<String, Class<? extends WebAction>> routes;

    public Application() {
        this.routes = new HashMap<>();

        this.server = new HttpServer(1408);
        this.server.addContext(new Context("/") {
            @Override
            public void onContext(HttpRequest request, HttpResponse response) {

                Class<? extends WebAction> actionClass = resolveRoute(request.getPath());

                try {
                    if (actionClass != null) {
                        Constructor<? extends WebAction> constructor = actionClass.getDeclaredConstructor(Application.class, HttpRequest.class, HttpResponse.class);
                        WebAction action = constructor.newInstance(Application.this, request, response);
                        action.execute();
                    }
                    else {
                        response.setResponseCode(HttpResponseCode.NOT_FOUND);
                        response.setBody("Path \"" + request.getPath() + "\" not found !!");
                    }
                }
                catch (Throwable throwable) {

                    try {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        PrintStream printer = new PrintStream(out);
                        throwable.printStackTrace(printer);
                        byte[] body = out.toByteArray();
                        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
                        response.setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
                        response.setBody(body);
                    }
                    catch (Throwable ex) {}
                }
            }
        });
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