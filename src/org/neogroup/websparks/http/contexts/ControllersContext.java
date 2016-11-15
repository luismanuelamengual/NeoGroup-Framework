
package org.neogroup.websparks.http.contexts;

import java.util.HashMap;
import java.util.Map;
import org.neogroup.websparks.controllers.Controller;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.routing.Route;

public class ControllersContext extends Context {

    private static final String CONTEXT_PATH_SEPARATOR = "/";
    
    private final Map<String, Controller> controllers;
    
    public ControllersContext() {
        this(".*"); 
    }
    
    public ControllersContext(String path) {
        super(path);
        controllers = new HashMap<>();
    }

    public void registerController (Controller controller) {
        
        Route routeAnnotation = controller.getClass().getAnnotation(Route.class);
        if (routeAnnotation != null) {
            String routePath = routeAnnotation.path();
            controllers.put(routePath, controller);
        }
    }
    
    @Override
    public HttpResponse onContext (HttpRequest request) {
        
        HttpResponse response = null;
        try {
            String controllerPath = getControllerPath(request);
            Controller controller = getController(controllerPath);
            if (controller == null) {
                throw new Exception ("Controller with path \"" + controllerPath + "\" not found !!");
            }
            
            String controllerAction = getControllerAction(request);
            response = controller.executeAction(controllerAction, request);
        }
        catch (Exception ex) {
            response = onError(request, ex);
        }
        return response;
    }

    private String getControllerPath (HttpRequest request) {
        String context = request.getPath();
        return context.substring(0, context.lastIndexOf(CONTEXT_PATH_SEPARATOR) + 1);
    }
    
    private String getControllerAction (HttpRequest request) {
        String context = request.getPath();
        return context.substring(context.lastIndexOf(CONTEXT_PATH_SEPARATOR) + 1);
    }
    
    private Controller getController (String controllerPath) {
        return controllers.get(controllerPath);
    }
}