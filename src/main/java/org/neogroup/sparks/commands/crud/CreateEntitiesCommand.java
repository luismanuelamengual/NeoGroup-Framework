
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;

import java.util.List;

/**
 * Command to create entities
 * @param <E> Type of entity
 */
public class CreateEntitiesCommand<E extends Entity> extends ModifyEntitiesCommand<E> {

    /**
     * Constructor of the command
     * @param entityClass entity class
     * @param entity entity
     */
    public CreateEntitiesCommand(Class<? extends E> entityClass, E entity) {
        super(entityClass, entity);
    }

    /**
     * Constructor of the command
     * @param entityClass entity class
     * @param entities entity
     */
    public CreateEntitiesCommand(Class<? extends E> entityClass, List<E> entities) {
        super(entityClass, entities);
    }
}
