
package org.neogroup.sparks.views;

public abstract class View {

    public abstract void setParameter (String name, Object value);
    public abstract Object getParameter (String name);
    public abstract String render ();
}
