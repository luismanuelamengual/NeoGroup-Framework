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
    private final Map<String, WebExecutor> controllers;

    public WebApplication() {

        super();
        this.controllers = new HashMap<>();
        this.server = new HttpServer(1408);
        this.server.addContext(new Context("/") {
            @Override
            public void onContext(HttpRequest request, HttpResponse response) {

                WebCommand command = new WebCommand(request, response);
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
                controllers.put(path, (WebExecutor) executor);
            }
        }
    }

    @Override
    protected Executor getExecutor(Command command) {
        Executor controller = null;
        if (command instanceof WebCommand) {
            WebCommand webCommand = (WebCommand)command;
            String path = webCommand.getRequest().getPath();
            controller = controllers.get(path);
        }
        else {
            controller = super.getExecutor(command);
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