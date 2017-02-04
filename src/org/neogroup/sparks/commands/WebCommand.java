
package org.neogroup.sparks.commands;

import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.sparks.processors.WebProcessorSelector;

@CommandComponent(selector = WebProcessorSelector.class)
public class WebCommand extends Command {

    public static final String CONTEXT_SEPARATOR = "/";

    private final HttpRequest request;

    public WebCommand(HttpRequest request) {
        this.request = request;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public String getWebRoute () {
        String path = request.getPath();
        return path.substring(0, path.lastIndexOf(CONTEXT_SEPARATOR) + 1);
    }

    public String getWebAction () {
        String path = request.getPath();
        return path.substring(path.lastIndexOf(CONTEXT_SEPARATOR) + 1);
    }
}
