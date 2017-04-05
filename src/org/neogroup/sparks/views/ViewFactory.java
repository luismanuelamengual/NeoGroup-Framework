
package org.neogroup.sparks.views;

public abstract class ViewFactory<T extends View> {

    public abstract T createView(String viewName) throws ViewException;
}
