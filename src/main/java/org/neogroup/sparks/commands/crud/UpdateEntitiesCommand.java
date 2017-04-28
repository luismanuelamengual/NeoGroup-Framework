
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.List;

public class UpdateEntitiesCommand<E extends Entity> extends ModifyEntitiesCommand<E> {

    public UpdateEntitiesCommand(Class<? extends E> entityClass, E resource) {
        super(entityClass, resource);
    }

    public UpdateEntitiesCommand(Class<? extends E> entityClass, List<E> resources) {
        super(entityClass, resources);
    }
}
