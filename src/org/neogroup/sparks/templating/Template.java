
package org.neogroup.sparks.templating;

public abstract class Template {

    public abstract void setParameter (String name, Object value);
    public abstract Object getParameter (String name);
    public abstract String render ();
}
