
package org.neogroup.sparks.resources.commands;

import org.neogroup.sparks.resources.Resource;

import java.util.List;

public class CreateResourcesCommand<R extends Resource> extends ModifyResourcesCommand<R> {

    public CreateResourcesCommand(Class<? extends R> resourceClass, R resource) {
        super(resourceClass, resource);
    }

    public CreateResourcesCommand(Class<? extends R> resourceClass, List<R> resources) {
        super(resourceClass, resources);
    }
}
