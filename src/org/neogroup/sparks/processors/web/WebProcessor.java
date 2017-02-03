
package org.neogroup.sparks.processors.web;

import org.neogroup.net.httpserver.HttpRequest;
import org.neogroup.net.httpserver.HttpResponse;
import org.neogroup.sparks.commands.web.WebCommand;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorComponent;

import java.util.List;

@ProcessorComponent(commands = {WebCommand.class})
public abstract class WebProcessor extends Processor<WebCommand, HttpResponse> {

    private static final String CONTEXT_PATH_SEPARATOR = "/";

    @Override
    public HttpResponse execute(WebCommand command) {
        String actionName = getActionName(command);
        return null;
    }

    private String getActionName (WebCommand command) {
        List<String> pathParts = command.getRequest().getPathParts();
        return pathParts.get(pathParts.size() - 1);
    }

    protected boolean onBeforeActionExecution (String actionName, HttpRequest request) {
        return true;
    }

    protected HttpResponse onAfterActionExecution (String actionName, HttpRequest request, Object response) {
        return (HttpResponse)response;
    }

    public static String getProcessorPath(HttpRequest request) {
        String context = request.getPath();
        return context.substring(0, context.lastIndexOf(CONTEXT_PATH_SEPARATOR) + 1);
    }

    public static String getProcessorAction(HttpRequest request) {
        String context = request.getPath();
        return context.substring(context.lastIndexOf(CONTEXT_PATH_SEPARATOR) + 1);
    }
}
