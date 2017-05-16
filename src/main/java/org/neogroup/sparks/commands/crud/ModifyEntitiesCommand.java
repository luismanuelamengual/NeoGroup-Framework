
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Command to modify entities
 * @param <E> entity type
 */
public abstract class ModifyEntitiesCommand<E extends Entity> extends CRUDCommand<E> {

    private final Collection<E> entities;

    /**
     * Command constructor
     * @param entityClass entity class
     * @param entity entity
     */
    public ModifyEntitiesCommand(Class<? extends E> entityClass, E entity) {
        super(entityClass);
        this.entities = new ArrayList<>();
        this.entities.add(entity);
    }

    /**
     * Command constructor
     * @param entityClass entity class
     * @param entities entities
     */
    public ModifyEntitiesCommand(Class<? extends E> entityClass, Collection<E> entities) {
        super(entityClass);
        this.entities = entities;
    }

    /**
     * Get the entities to modify
     * @return collection of entities
     */
    public Collection<E> getEntities() {
        return entities;
    }
}
