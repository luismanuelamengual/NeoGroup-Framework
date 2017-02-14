
package org.neogroup.sparks.templating;

public abstract class Template {

    protected final String filename;

    public Template(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public abstract void setParameter (String name, Object value);
    public abstract Object getParameter (String name);
    public abstract String render ();
}
