
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.model.Entity;
import org.neogroup.sparks.model.EntityFilter;
import org.neogroup.sparks.model.EntitySorter;

import java.util.List;

public class RetrieveEntitiesCommand<E extends Entity> extends CRUDCommand<E> {

    private EntityFilter filters;
    private List<EntitySorter> orders;

    public RetrieveEntitiesCommand(Class<? extends E> resourceClass) {
        super(resourceClass);
    }

    public EntityFilter getFilters() {
        return filters;
    }

    public void setFilters(EntityFilter filters) {
        this.filters = filters;
    }

    public List<EntitySorter> getOrders() {
        return orders;
    }

    public void setOrders(List<EntitySorter> orders) {
        this.orders = orders;
    }
}
