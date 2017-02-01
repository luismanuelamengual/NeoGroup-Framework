
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.WebCommand;

import java.util.List;

public class WebProcessor extends Processor<WebCommand> {

    @Override
    public Object execute(WebCommand command) {
        String actionName = getActionName(command);
        return null;
    }

    private String getActionName (WebCommand command) {
        List<String> pathParts = command.getRequest().getPathParts();
        return pathParts.get(pathParts.size() - 1);
    }

    protected boolean onBeforeActionExecution (String actionName, WebCommand command) {
        return true;
    }

    protected Object onAfterActionExecution (String actionName, WebCommand command, Object response) {
        return response;
    }
}
