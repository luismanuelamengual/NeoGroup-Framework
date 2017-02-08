
package org.neogroup.sparks.commands;

import org.neogroup.sparks.processors.ResourceProcessor;
import org.neogroup.sparks.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public abstract class ModifyResourcesCommand<R extends Resource> extends ResourcesCommand<R> {

    private final List<R> resources;

    public ModifyResourcesCommand(Class<? extends R> resourceClass, R resource) {
        super(resourceClass);
        this.resources = new ArrayList<>();
        this.resources.add(resource);
    }

    public ModifyResourcesCommand(Class<? extends R> resourceClass, List<R> resources) {
        super(resourceClass);
        this.resources = resources;
    }

    public List<R> getResources() {
        return resources;
    }
}
