
package org.neogroup.sparks.model;

/**
 * Entity sorter
 */
public class EntitySorter {

    private final String property;
    private final EntitySorterDirection direction;

    /**
     * Constructor of the entity sorter
     * @param property property to sort
     */
    public EntitySorter(String property) {
        this(property, EntitySorterDirection.ASC);
    }

    /**
     * Constructor of the entity sorter
     * @param property property of sort
     * @param direction direction of sorting
     */
    public EntitySorter(String property, EntitySorterDirection direction) {
        this.property = property;
        this.direction = direction;
    }

    /**
     * Get the property of the entity
     * @return String property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Get the direction of sorting for the entity
     * @return sorter direction
     */
    public EntitySorterDirection getDirection() {
        return direction;
    }
}
