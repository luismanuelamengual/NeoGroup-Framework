
package org.neogroup.sparks.commands.web;

import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.sparks.commands.Command;

public class WebCommand extends Command {

    private final HttpRequest request;

    public WebCommand(HttpRequest request) {
        this.request = request;
    }

    public HttpRequest getRequest() {
        return request;
    }
}
