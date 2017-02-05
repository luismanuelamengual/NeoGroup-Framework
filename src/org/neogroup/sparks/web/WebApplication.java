
package org.neogroup.sparks.web;

import org.neogroup.httpserver.HttpContext;
import org.neogroup.httpserver.HttpRequest;
import org.neogroup.httpserver.HttpResponse;
import org.neogroup.httpserver.HttpServer;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.web.commands.WebCommand;

public class WebApplication extends Application {

    public static final String SERVER_PORT_PROPERTY = "server_port";

    private final HttpServer server;

    public WebApplication() {
        server = new HttpServer(properties.getInt(SERVER_PORT_PROPERTY, 80));
        server.addContext(new HttpContext("/") {
            @Override
            public HttpResponse onContext(HttpRequest request) {
                return WebApplication.this.executeCommand(new WebCommand(request));
            }
        });
    }

    @Override
    public void start() {
        server.start();
    }

    @Override
    public void stop() {
        server.stop();
    }

    public void addContext(HttpContext context) {
        server.addContext(context);
    }

    public void removeContext(HttpContext context) {
        server.removeContext(context);
    }
}
