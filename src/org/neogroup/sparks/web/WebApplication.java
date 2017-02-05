
package org.neogroup.sparks.web;

import org.neogroup.httpserver.*;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
import org.neogroup.sparks.web.commands.WebCommand;
import org.neogroup.util.MimeTypes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class WebApplication extends Application {

    public static final String SERVER_PORT_PROPERTY = "server_port";

    private final HttpServer server;

    public WebApplication() {
        server = new HttpServer(properties.getInt(SERVER_PORT_PROPERTY, 80));
        server.addContext(new HttpContext("/") {
            @Override
            public HttpResponse onContext(HttpRequest request) {
                WebCommand webCommand = new WebCommand(request);
                HttpResponse response = null;
                try {
                    response = WebApplication.this.executeCommand(webCommand);
                }
                catch (ProcessorNotFoundException exception) {
                    response = onContextNotFound(webCommand);
                }
                catch (Throwable throwable) {
                    response = onError(webCommand, throwable);
                }
                return response;
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

    public HttpResponse onContextNotFound (WebCommand command) {
        HttpResponse response = new HttpResponse();
        response.setResponseCode(HttpResponseCode.HTTP_NOT_FOUND);
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
        response.setBody("No controller found for path \"" + command.getWebRoute() + "\" !!");
        return response;
    }

    public HttpResponse onError (WebCommand command, Throwable throwable) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(out);
        throwable.printStackTrace(printer);
        byte[] body = out.toByteArray();
        HttpResponse response = new HttpResponse();
        response.setResponseCode(HttpResponseCode.HTTP_INTERNAL_ERROR);
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
        response.setBody(body);
        return response;
    }
}
