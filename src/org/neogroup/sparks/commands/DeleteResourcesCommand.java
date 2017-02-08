
package org.neogroup.sparks.commands;

import org.neogroup.sparks.resources.Resource;

import java.util.List;

public class DeleteResourcesCommand<R extends Resource> extends ModifyResourcesCommand<R> {

    public DeleteResourcesCommand(Class<? extends R> resourceClass, R resource) {
        super(resourceClass, resource);
    }

    public DeleteResourcesCommand(Class<? extends R> resourceClass, List<R> resources) {
        super(resourceClass, resources);
    }
}
