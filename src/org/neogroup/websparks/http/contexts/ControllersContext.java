
package org.neogroup.websparks.http.contexts;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.neogroup.websparks.controllers.Controller;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.routing.Route;
import org.neogroup.websparks.routing.RouteAction;

public class ControllersContext extends Context {

    private static final String CONTEXT_PATH_SEPARATOR = "/";
    
    private final Map<String, Controller> controllers;
    private final Map<Controller, Map<String, Method>> controllerActions;
    
    public ControllersContext() {
        this("/"); 
    }
    
    public ControllersContext(String path) {
        super(path);
        controllers = new HashMap<>();
        controllerActions = new HashMap<>();
    }

    public void registerController (Controller controller) {
        
        Class<? extends Controller> controllerClass = controller.getClass();
        Route routeAnnotation = controllerClass.getAnnotation(Route.class);
        if (routeAnnotation != null) {
            String routePath = routeAnnotation.path();
            controllers.put(routePath, controller);
            
            Map<String, Method> actions = new HashMap<>();
            for (Method method : controllerClass.getMethods()) {
                RouteAction routeActionAnnotation = method.getAnnotation(RouteAction.class);
                if (routeActionAnnotation != null) {
                    String routeAction = routeActionAnnotation.name();
                    actions.put(routeAction, method);
                }
            }
            controllerActions.put(controller, actions);
        }
    }
    
    @Override
    public HttpResponse onContext (HttpRequest request) {
        
        HttpResponse response = null;
        try {
            String controllerPath = getControllerPath(request);
            Controller controller = controllers.get(controllerPath);
            if (controller == null) {
                throw new Exception ("Controller with path \"" + controllerPath + "\" not found !!");
            }
            
            String controllerAction = getControllerAction(request);
            Method controllerMethod = controllerActions.get(controller).get(controllerAction);
            if (controllerMethod == null) {
                throw new Exception ("Action \"" + controllerAction + "\" not found in controller \"" + controller.toString() + "\" !!");
            }
            
            response = (HttpResponse)controllerMethod.invoke(controller, request);
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
}