
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.List;

public class DeleteEntitiesCommand<E extends Entity> extends ModifyEntitiesCommand<E> {

    public DeleteEntitiesCommand(Class<? extends E> resourceClass, E resource) {
        super(resourceClass, resource);
    }

    public DeleteEntitiesCommand(Class<? extends E> resourceClass, List<E> resources) {
        super(resourceClass, resources);
    }
}
