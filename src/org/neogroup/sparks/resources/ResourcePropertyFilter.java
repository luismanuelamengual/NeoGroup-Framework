
package org.neogroup.sparks.resources;

public class ResourcePropertyFilter extends ResourceFilter {

    private final String property;
    private final String connector;
    private final Object value;

    public ResourcePropertyFilter(String property, Object value) {
        this(property, ResourcePropertyOperator.EQUALS, value);
    }

    public ResourcePropertyFilter(String property, String connector, Object value) {
        this.property = property;
        this.connector = connector;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public String getConnector() {
        return connector;
    }

    public Object getValue() {
        return value;
    }
}
