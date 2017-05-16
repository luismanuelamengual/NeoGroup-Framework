
package org.neogroup.sparks.model;

/**
 * Property filter for entities
 */
public class EntityPropertyFilter extends EntityFilter {

    private final String property;
    private final String operator;
    private final Object value;

    /**
     * Constructor for property filter
     * @param property entity property
     * @param value value
     */
    public EntityPropertyFilter(String property, Object value) {
        this(property, EntityPropertyOperator.EQUALS, value);
    }

    /**
     * Constructor for property filter
     * @param property entity property
     * @param operator operator
     * @param value value
     */
    public EntityPropertyFilter(String property, String operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    /**
     * Get the name of the property of the entity
     * @return String name of property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Get the operator of the filter
     * @return String name of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Get the value of the filter
     * @return Object value of filter
     */
    public Object getValue() {
        return value;
    }
}
