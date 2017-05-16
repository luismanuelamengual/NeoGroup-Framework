
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.List;

/**
 * Command to update entities
 * @param <E> entity type
 */
public class UpdateEntitiesCommand<E extends Entity> extends ModifyEntitiesCommand<E> {

    /**
     * Command constructor
     * @param entityClass entity class
     * @param entity entity
     */
    public UpdateEntitiesCommand(Class<? extends E> entityClass, E entity) {
        super(entityClass, entity);
    }

    /**
     * Command constructor
     * @param entityClass entity class
     * @param entities entities
     */
    public UpdateEntitiesCommand(Class<? extends E> entityClass, List<E> entities) {
        super(entityClass, entities);
    }
}
