
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.List;

public class CreateEntitiesCommand<E extends Entity> extends ModifyEntitiesCommand<E> {

    public CreateEntitiesCommand(Class<? extends E> resourceClass, E resource) {
        super(resourceClass, resource);
    }

    public CreateEntitiesCommand(Class<? extends E> resourceClass, List<E> resources) {
        super(resourceClass, resources);
    }
}
