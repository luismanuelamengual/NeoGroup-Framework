
package org.neogroup.sparks.models;

public abstract class Model<I extends Object> {

    public abstract I getId();
    public abstract void setId(I id);
}
