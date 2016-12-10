package org.neogroup.sparks.web;

import org.neogroup.sparks.Action;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.Executor;
import org.neogroup.sparks.ExecutorNotFoundException;
import org.neogroup.sparks.util.MimeTypes;
import org.neogroup.sparks.web.http.*;
import org.neogroup.sparks.web.http.contexts.Context;
import org.neogroup.sparks.web.http.contexts.FolderContext;
import org.neogroup.sparks.web.http.contexts.ResourcesContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        if (action instanceof WebAction) {
            WebAction webAction = (WebAction)action;
            if (throwable instanceof ExecutorNotFoundException) {
                onRouteNotFound(webAction.getRequest(), webAction.getResponse());
            }
            else {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PrintStream printer = new PrintStream(out);
                throwable.printStackTrace(printer);
                byte[] body = out.toByteArray();
                webAction.getResponse().addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
                webAction.getResponse().setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
                webAction.getResponse().setBody(body);
                response = super.onActionError(action, throwable);
            }
        }
        else {
            response = super.onActionError(action, throwable);
        }
        return response;
    }

    protected void onRouteNotFound (HttpRequest request, HttpResponse response) {
        response.setResponseCode(HttpResponseCode.NOT_FOUND);
        response.write("Executor not found for path \"%s\"", request.getPath());
    }

    public void registerResourcesContext (String contextPath, String resourceFolder) {
        this.server.addContext(new ResourcesContext(contextPath, resourceFolder));
    }

    public void registerFolderContext(String contextPath, String folder) {
        this.server.addContext(new FolderContext(contextPath, folder));
    }

    public void startServer () {
        server.start();
        getLogger().info("Web Server started !!" );
    }

    public void stopServer () {
        server.stop();
        getLogger().info("Web Server stopped !!" );
    }
}