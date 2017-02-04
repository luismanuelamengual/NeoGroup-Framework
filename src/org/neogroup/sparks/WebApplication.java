
package org.neogroup.sparks;

import org.neogroup.net.httpserver.HttpContext;
import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;
import org.neogroup.net.httpserver.HttpServer;
import org.neogroup.sparks.commands.WebCommand;

public class WebApplication extends Application {

    private final HttpServer server;

    public WebApplication() {
        server = new HttpServer(1408);
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
