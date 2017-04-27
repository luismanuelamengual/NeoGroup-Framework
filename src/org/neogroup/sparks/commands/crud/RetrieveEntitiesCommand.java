
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.model.EntityQuery;

public class RetrieveEntitiesCommand<E extends Entity> extends CRUDCommand<E> {

    private final EntityQuery query;

    public RetrieveEntitiesCommand(Class<? extends E> entityClass, EntityQuery query) {
        super(entityClass);
        this.query = query;
    }

    public EntityQuery getQuery() {
        return query;
    }
}
