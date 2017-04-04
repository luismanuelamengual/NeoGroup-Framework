
package org.neogroup.sparks.resources;

public class ResourcePropertyFilter extends ResourceFilter {

    private final String property;
    private final String operator;
    private final Object value;

    public ResourcePropertyFilter(String property, Object value) {
        this(property, ResourcePropertyOperator.EQUALS, value);
    }

    public ResourcePropertyFilter(String property, String operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public String getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}
