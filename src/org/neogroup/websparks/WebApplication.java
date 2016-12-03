package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.http.contexts.Context;
import org.neogroup.websparks.http.contexts.FolderContext;

import java.util.HashMap;
import java.util.Map;

public class WebApplication extends Application {

    private final HttpServer server;
    private final Map<String, WebExecutor> executors;

    public WebApplication() {

        super();
        this.executors = new HashMap<>();
        this.server = new HttpServer(1408);
        this.server.addContext(new Context("/") {
            @Override
            public void onContext(HttpRequest request, HttpResponse response) {

                WebAction command = new WebAction(request, response);
                executeCommand(command);
            }
        });
    }

    @Override
    protected void registerExecutor(Executor executor) {
        super.registerExecutor(executor);

        if (executor instanceof WebExecutor) {
            WebRoute webRouteAnnotation = executor.getClass().getAnnotation(WebRoute.class);
            if (webRouteAnnotation != null) {
                String path = webRouteAnnotation.path();
                executors.put(path, (WebExecutor) executor);
            }
        }
    }

    @Override
    protected Executor getExecutor(Action action) {
        Executor controller = null;
        if (action instanceof WebAction) {
            WebAction webCommand = (WebAction) action;
            String path = webCommand.getRequest().getPath();
            controller = executors.get(path);
        }
        else {
            controller = super.getExecutor(action);
        }
        return controller;
    }

    public void registerResourcesContext (String contextPath, String folder) {
        this.server.addContext(new FolderContext(contextPath, folder));
    }

    public void startServer () {
        server.start();
    }

    public void stopServer () {
        server.stop();
    }
}