
package org.neogroup.sparks.model;

public class EntitySorter {

    private final String property;
    private final EntitySorterDirection direction;

    public EntitySorter(String property) {
        this(property, EntitySorterDirection.ASC);
    }

    public EntitySorter(String property, EntitySorterDirection direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public EntitySorterDirection getDirection() {
        return direction;
    }
}
