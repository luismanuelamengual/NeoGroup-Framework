
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.List;

/**
 * Command to delete entities
 * @param <E> entity type
 */
public class DeleteEntitiesCommand<E extends Entity> extends ModifyEntitiesCommand<E> {

    /**
     * Command constructor
     * @param entityClass entity class
     * @param entity entity
     */
    public DeleteEntitiesCommand(Class<? extends E> entityClass, E entity) {
        super(entityClass, entity);
    }

    /**
     * Command constructor
     * @param entityClass entity class
     * @param entities entity
     */
    public DeleteEntitiesCommand(Class<? extends E> entityClass, List<E> entities) {
        super(entityClass, entities);
    }
}
