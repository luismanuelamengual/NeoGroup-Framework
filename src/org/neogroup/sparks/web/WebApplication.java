
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
                HttpResponse response = null;
                try {
                    response = WebApplication.this.executeCommand(new WebCommand(request));
                }
                catch (ProcessorNotFoundException exception) {
                    response = onContextNotFound(request);
                }
                catch (Throwable throwable) {
                    response = onError(request, throwable);
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

    public HttpResponse onContextNotFound (HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.setResponseCode(HttpResponseCode.HTTP_NOT_FOUND);
        response.addHeader(HttpHeader.CONTENT_TYPE, MimeTypes.TEXT_PLAIN);
        response.setBody("No controller found for path \"" + request.getPath() + "\" !!");
        return response;
    }

    public HttpResponse onError (HttpRequest request, Throwable throwable) {
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
