
package org.neogroup.sparks.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Group of entity filters
 */
public class EntityFilterGroup extends EntityFilter {

    private EntityFilterGroupConnector connector;
    private final List<EntityFilter> filters;

    /**
     * Constructor for the group of filters
     */
    public EntityFilterGroup() {
        filters = new ArrayList<>();
        connector = EntityFilterGroupConnector.AND;
    }

    /**
     * Get the connector of this group of filters
     * @return EntityFilterGroupConnector connector
     */
    public EntityFilterGroupConnector getConnector() {
        return connector;
    }

    /**
     * Set the connector for this group of filters
     * @param connector connector
     */
    public void setConnector(EntityFilterGroupConnector connector) {
        this.connector = connector;
    }

    /**
     * Get the filters of this group
     * @return list of filters
     */
    public List<EntityFilter> getFilters() {
        return filters;
    }

    /**
     * Get the amount of filters of this group
     * @return int
     */
    public int getSize() {
        return filters.size();
    }

    /**
     * Indicates if this group is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return filters.isEmpty();
    }

    /**
     * Add a new filter to the group
     * @param filter filter
     */
    public void addFilter (EntityFilter filter) {
        filters.add(filter);
    }

    /**
     * Removes a filter from the group
     * @param filter filter
     */
    public void removeFilter (EntityFilter filter) {
        filters.remove(filter);
    }
}
