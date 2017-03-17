
package org.neogroup.sparks.resources;

public abstract class Resource<I extends Object> {

    public abstract I getId();
    public abstract void setId(I id);
}
