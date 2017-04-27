
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.List;

public class DeleteEntitiesCommand<E extends Entity> extends ModifyEntitiesCommand<E> {

    public DeleteEntitiesCommand(Class<? extends E> entityClass, E resource) {
        super(entityClass, resource);
    }

    public DeleteEntitiesCommand(Class<? extends E> entityClass, List<E> resources) {
        super(entityClass, resources);
    }
}
