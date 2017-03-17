
package org.neogroup.sparks.resources.commands;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.resources.Resource;

import java.util.Map;

public abstract class ResourcesCommand<R extends Resource> extends Command {

    public static final String START_PARAMETER = "start";
    public static final String LIMIT_PARAMETER = "limit";

    private final Class<? extends R> resourceClass;
    private Map<String,Object> parameters;

    public ResourcesCommand(Class<? extends R> resourceClass) {
        this.resourceClass = resourceClass;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Class<? extends R> getResourceClass() {
        return resourceClass;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter (String parameter, Object value) {
        parameters.put(parameter, value);
    }
}
