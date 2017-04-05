
package org.neogroup.sparks.commands.crud;

import org.neogroup.sparks.models.Model;
import org.neogroup.sparks.models.ModelFilter;
import org.neogroup.sparks.models.ModelSorter;

import java.util.List;

public class RetrieveEntitiesCommand<M extends Model> extends CRUDCommand<M> {

    private ModelFilter filters;
    private List<ModelSorter> orders;

    public RetrieveEntitiesCommand(Class<? extends M> resourceClass) {
        super(resourceClass);
    }

    public ModelFilter getFilters() {
        return filters;
    }

    public void setFilters(ModelFilter filters) {
        this.filters = filters;
    }

    public List<ModelSorter> getOrders() {
        return orders;
    }

    public void setOrders(List<ModelSorter> orders) {
        this.orders = orders;
    }
}
