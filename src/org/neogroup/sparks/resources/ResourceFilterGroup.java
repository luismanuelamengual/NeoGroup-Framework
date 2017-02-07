
package org.neogroup.sparks.resources;

import java.util.ArrayList;
import java.util.List;

public class ResourceFilterGroup extends ResourceFilter {

    private ResourceFilterGroupConnector connector;
    private List<ResourceFilter> filters;

    public ResourceFilterGroup() {
        filters = new ArrayList<>();
    }

    public ResourceFilterGroupConnector getConnector() {
        return connector;
    }

    public void setConnector(ResourceFilterGroupConnector connector) {
        this.connector = connector;
    }

    public List<ResourceFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<ResourceFilter> filters) {
        this.filters = filters;
    }

    public void addFilter (ResourceFilter filter) {
        filters.add(filter);
    }

    public void removeFilter (ResourceFilter filter) {
        filters.remove(filter);
    }
}
