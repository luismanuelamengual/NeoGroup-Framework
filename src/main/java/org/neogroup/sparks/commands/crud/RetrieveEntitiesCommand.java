
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.model.EntityQuery;

/**
 * Command to retrieve entities
 * @param <E> entity type
 */
public class RetrieveEntitiesCommand<E extends Entity> extends CRUDCommand<E> {

    private final EntityQuery query;

    /**
     * Command constructor
     * @param entityClass entity class
     * @param query entity query
     */
    public RetrieveEntitiesCommand(Class<? extends E> entityClass, EntityQuery query) {
        super(entityClass);
        this.query = query;
    }

    /**
     * Get the query for entities
     * @return entity query
     */
    public EntityQuery getQuery() {
        return query;
    }
}
