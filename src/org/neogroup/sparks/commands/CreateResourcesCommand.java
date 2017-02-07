
package org.neogroup.sparks.commands;

import org.neogroup.sparks.resources.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateResourcesCommand<R extends Resource> extends ResourcesCommand<R> {

    private List<R> resources;
    private Map<String,Object> parameters;

    public CreateResourcesCommand() {
        super(ResourcesCommandType.CREATE);
        this.resources = new ArrayList<>();
    }

    public void addResource (R resource) {
        resources.add(resource);
    }

    public List<R> getResources() {
        return resources;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
