
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class ModifyEntitiesCommand<E extends Entity> extends CRUDCommand<E> {

    private final List<E> entities;

    public ModifyEntitiesCommand(Class<? extends E> entityClass, E resource) {
        super(entityClass);
        this.entities = new ArrayList<>();
        this.entities.add(resource);
    }

    public ModifyEntitiesCommand(Class<? extends E> entityClass, List<E> entities) {
        super(entityClass);
        this.entities = entities;
    }

    public List<E> getEntities() {
        return entities;
    }
}
