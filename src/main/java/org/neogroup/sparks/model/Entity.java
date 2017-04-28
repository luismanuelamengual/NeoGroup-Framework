
package org.neogroup.sparks.model;

public abstract class Entity<I extends Object> {

    public abstract I getId();
    public abstract void setId(I id);
}
