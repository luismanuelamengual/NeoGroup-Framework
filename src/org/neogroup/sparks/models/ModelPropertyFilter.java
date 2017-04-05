
package org.neogroup.sparks.models;

public class ModelPropertyFilter extends ModelFilter {

    private final String property;
    private final String operator;
    private final Object value;

    public ModelPropertyFilter(String property, Object value) {
        this(property, ModelPropertyOperator.EQUALS, value);
    }

    public ModelPropertyFilter(String property, String operator, Object value) {
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
