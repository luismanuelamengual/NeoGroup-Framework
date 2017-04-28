
package org.neogroup.sparks.model;

public class EntityPropertyFilter extends EntityFilter {

    private final String property;
    private final String operator;
    private final Object value;

    public EntityPropertyFilter(String property, Object value) {
        this(property, EntityPropertyOperator.EQUALS, value);
    }

    public EntityPropertyFilter(String property, String operator, Object value) {
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
