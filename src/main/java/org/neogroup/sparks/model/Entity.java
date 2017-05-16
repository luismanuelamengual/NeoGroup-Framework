
package org.neogroup.sparks.model;

/**
 * Entities for sparks framework
 * @param <I> Type of id property
 */
public abstract class Entity<I extends Object> {

    /**
     * Get the id of the entity
     * @return I id
     */
    public abstract I getId();

    /**
     * Sets the id of the entity
     * @param id id
     */
    public abstract void setId(I id);
}
