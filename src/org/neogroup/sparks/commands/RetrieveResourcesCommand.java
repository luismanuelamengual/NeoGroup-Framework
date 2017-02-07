
package org.neogroup.sparks.commands;

import org.neogroup.sparks.resources.Resource;
import org.neogroup.sparks.resources.ResourceFilter;
import org.neogroup.sparks.resources.ResourceOrder;

import java.util.List;
import java.util.Map;

public class RetrieveResourcesCommand<R extends Resource> extends ResourcesCommand<R> {

    private Class<R> resourceClass;
    private ResourceFilter filters;
    private List<ResourceOrder> orders;
    private Map<String,Object> parameters;

    public RetrieveResourcesCommand() {
        super(ResourcesCommandType.RETRIEVE);
    }

    public Class<R> getResourceClass() {
        return resourceClass;
    }

    public void setResourceClass(Class<R> resourceClass) {
        this.resourceClass = resourceClass;
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

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
