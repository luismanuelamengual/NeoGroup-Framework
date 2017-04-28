
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ModifyEntitiesCommand<E extends Entity> extends CRUDCommand<E> {

    private final Collection<E> entities;

    public ModifyEntitiesCommand(Class<? extends E> entityClass, E resource) {
        super(entityClass);
        this.entities = new ArrayList<>();
        this.entities.add(resource);
    }

    public ModifyEntitiesCommand(Class<? extends E> entityClass, Collection<E> entities) {
        super(entityClass);
        this.entities = entities;
    }

    public Collection<E> getEntities() {
        return entities;
    }
}
