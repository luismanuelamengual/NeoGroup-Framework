
package org.neogroup.sparks.models;

public class ModelSorter {

    private final String property;
    private final ModelSorterDirection direction;

    public ModelSorter(String property) {
        this(property, ModelSorterDirection.ASC);
    }

    public ModelSorter(String property, ModelSorterDirection direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public ModelSorterDirection getDirection() {
        return direction;
    }
}
