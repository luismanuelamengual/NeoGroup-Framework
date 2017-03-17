
package org.neogroup.sparks.resources.commands;

import org.neogroup.sparks.resources.Resource;

import java.util.List;

public class UpdateResourcesCommand<R extends Resource> extends ModifyResourcesCommand<R> {

    public UpdateResourcesCommand(Class<? extends R> resourceClass, R resource) {
        super(resourceClass, resource);
    }

    public UpdateResourcesCommand(Class<? extends R> resourceClass, List<R> resources) {
        super(resourceClass, resources);
    }
}
