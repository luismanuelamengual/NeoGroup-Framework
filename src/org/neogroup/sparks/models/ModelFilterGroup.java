
package org.neogroup.sparks.models;

import java.util.ArrayList;
import java.util.List;

public class ModelFilterGroup extends ModelFilter {

    private ModelFilterGroupConnector connector;
    private List<ModelFilter> filters;

    public ModelFilterGroup() {
        filters = new ArrayList<>();
    }

    public ModelFilterGroupConnector getConnector() {
        return connector;
    }

    public void setConnector(ModelFilterGroupConnector connector) {
        this.connector = connector;
    }

    public List<ModelFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<ModelFilter> filters) {
        this.filters = filters;
    }

    public void addFilter (ModelFilter filter) {
        filters.add(filter);
    }

    public void removeFilter (ModelFilter filter) {
        filters.remove(filter);
    }
}
