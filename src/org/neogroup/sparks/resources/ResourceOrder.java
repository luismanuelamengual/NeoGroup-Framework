
package org.neogroup.sparks.resources;

public class ResourceOrder {

    private final String property;
    private final ResourceOrderDirection direction;

    public ResourceOrder(String property) {
        this(property, ResourceOrderDirection.ASC);
    }

    public ResourceOrder(String property, ResourceOrderDirection direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public ResourceOrderDirection getDirection() {
        return direction;
    }
}
