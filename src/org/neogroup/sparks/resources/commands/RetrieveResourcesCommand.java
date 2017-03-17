
package org.neogroup.sparks.resources.commands;

import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;

import java.util.List;

public class RetrieveResourcesCommand<R extends Resource> extends ResourcesCommand<R> {

    private ResourceFilter filters;
    private List<ResourceOrder> orders;

    public RetrieveResourcesCommand(Class<? extends R> resourceClass) {
        super(resourceClass);
    }

    public ResourceFilter getFilters() {
        return filters;
    }

    public void setFilters(ResourceFilter filters) {
        this.filters = filters;
    }

    public List<ResourceOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ResourceOrder> orders) {
        this.orders = orders;
    }
}
