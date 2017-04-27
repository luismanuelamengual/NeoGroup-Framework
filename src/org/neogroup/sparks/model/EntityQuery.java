package org.neogroup.sparks.model;

import java.util.ArrayList;
import java.util.List;

public class EntityQuery {

    private final EntityFilterGroup filters;
    private final List<EntitySorter> sorters;
    private Integer start;
    private Integer limit;

    public EntityQuery() {
        this.filters = new EntityFilterGroup();
        this.sorters = new ArrayList<EntitySorter>();
        this.start = null;
        this.limit = null;
    }

    public EntityFilterGroup getFilters() {
        return filters;
    }

    public List<EntitySorter> getSorters() {
        return sorters;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setFiltersConnector (EntityFilterGroupConnector connector) {
        filters.setConnector(connector);
    }

    public EntityFilterGroupConnector getFiltersConnector() {
        return filters.getConnector();
    }

    public void addFilter (EntityFilter filter) {
        filters.addFilter(filter);
    }

    public EntityPropertyFilter addFilter (String property, Object value) {
        EntityPropertyFilter filter = new EntityPropertyFilter(property, value);
        addFilter(filter);
        return filter;
    }

    public EntityPropertyFilter addFilter (String property, String operator, Object value) {
        EntityPropertyFilter filter = new EntityPropertyFilter(property, operator, value);
        addFilter(filter);
        return filter;
    }

    public void removeFilter (EntityFilter filter) {
        filters.removeFilter(filter);
    }

    public void addSorter (EntitySorter sorter) {
        sorters.add(sorter);
    }

    public EntitySorter addSorter (String property) {
        EntitySorter sorter = new EntitySorter(property);
        addSorter(sorter);
        return sorter;
    }

    public EntitySorter addSorter (String property, EntitySorterDirection direction) {
        EntitySorter sorter = new EntitySorter(property, direction);
        addSorter(sorter);
        return sorter;
    }

    public void removeSorter (EntitySorter sorter) {
        sorters.remove(sorter);
    }
}
