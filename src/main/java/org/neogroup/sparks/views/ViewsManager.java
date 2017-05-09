
package org.neogroup.sparks.views;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.Manager;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ViewsManager extends Manager {

    private static final String DEFAULT_VIEW_FACTORY_PROPERTY = "defaultViewFactory";
    private static final String VIEW_NOT_FOUND_ERROR = "View \"{0}\" not found !!";

    private final Map<String, ViewFactory> viewFactories;

    public ViewsManager(ApplicationContext applicationContext) {
        super(applicationContext);
        this.viewFactories = new HashMap<>();
    }

    public void addViewFactory(String viewFactoryName, ViewFactory viewFactory) {
        viewFactories.put(viewFactoryName, viewFactory);
    }

    public void removeViewFactory(String viewFactoryName) {
        viewFactories.remove(viewFactoryName);
    }

    public View createView(String viewName) throws ViewException {

        View view = null;
        if (viewFactories.size() == 1) {
            view = createView(viewFactories.keySet().iterator().next(), viewName);
        }
        else if (applicationContext.getProperties().contains(DEFAULT_VIEW_FACTORY_PROPERTY)) {
            view = createView(applicationContext.getProperties().get(DEFAULT_VIEW_FACTORY_PROPERTY), viewName);
        }
        return view;
    }

    public View createView(String viewFactoryName, String viewName) throws ViewException {

        View view = null;
        ViewFactory viewFactory = viewFactories.get(viewFactoryName);
        if (viewFactory != null) {
            view = viewFactory.createView(viewName);
        }
        if (view == null) {
            throw new ViewNotFoundException(MessageFormat.format(VIEW_NOT_FOUND_ERROR, viewName));
        }
        return view;
    }
}
