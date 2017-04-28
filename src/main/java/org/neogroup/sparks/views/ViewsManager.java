
package org.neogroup.sparks.views;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

public class ViewsManager {

    private static final String VIEW_NOT_FOUND_ERROR = "View \"{0}\" not found !!";

    private final Set<ViewFactory> viewFactories;

    public ViewsManager() {
        this.viewFactories = new HashSet<>();
    }

    public void addViewFactory(ViewFactory viewFactory) {
        viewFactories.add(viewFactory);
    }

    public void removeViewFactory(ViewFactory viewFactory) {
        viewFactories.remove(viewFactory);
    }

    public View createView(String viewName) throws ViewException {

        View view = null;
        if (viewName != null && !viewName.isEmpty()) {
            for (ViewFactory viewFactory : viewFactories) {
                try {
                    view = viewFactory.createView(viewName);
                    if (view != null) {
                        break;
                    }
                } catch (ViewNotFoundException ex) {
                }
            }
        }
        if (view == null) {
            throw new ViewNotFoundException(MessageFormat.format(VIEW_NOT_FOUND_ERROR, viewName));
        }
        return view;
    }
}
