package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpResponseCode;
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
                WebAction action = new WebAction(request, response);
                executeAction(action);
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
        Executor executor = null;
        if (action instanceof WebAction) {
            WebAction webAction = (WebAction) action;
            String path = webAction.getRequest().getPath();
            executor = executors.get(path);
        }
        else {
            executor = super.getExecutor(action);
        }
        return executor;
    }

    @Override
    protected Object onActionError(Action action, Throwable throwable) {
        Object response = null;
        if (action instanceof WebAction && throwable instanceof ExecutorNotFoundException) {
            WebAction webAction = (WebAction)action;
            onRouteNotFound(webAction.getRequest(), webAction.getResponse());
        }
        else {
            response = super.onActionError(action, throwable);
        }
        return response;
    }

    protected void onRouteNotFound (HttpRequest request, HttpResponse response) {
        response.setResponseCode(HttpResponseCode.NOT_FOUND);
        response.write("Executor not found for path \"" + request.getPath() + "\"");
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