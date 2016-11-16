
package org.neogroup.websparks.http.contexts;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.neogroup.websparks.Controller;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpResponseCode;
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

    public void registerController (Class<? extends Controller> controllerClass) {
        
        Route routeAnnotation = controllerClass.getAnnotation(Route.class);
        if (routeAnnotation != null) {
            String routePath = routeAnnotation.path();
            try {
                Controller controller = controllerClass.newInstance();
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
            catch (Exception ex) {
                throw new RuntimeException("Could not create instance of controller !!", ex);
            }
        }
    }
    
    @Override
    public void onContext (HttpRequest request, HttpResponse response) {
        
        String controllerPath = getControllerPath(request);
        Controller controller = controllers.get(controllerPath);
        if (controller == null) {
            onNotFound (request, response, "Controller with path \"" + controllerPath + "\" not found !!");
        }
        else {

            String controllerAction = getControllerAction(request);
            Method controllerMethod = controllerActions.get(controller).get(controllerAction);
            if (controllerMethod == null) {
                onNotFound (request, response, "Action \"" + controllerAction + "\" not found in controller \"" + controller.toString() + "\" !!");
            }
            
            try {
                controllerMethod.invoke(controller);
            }
            catch (Throwable ex) {
                onError(request, response, ex.getCause());
            }
        }
    }
    
    protected void onNotFound (HttpRequest request, HttpResponse response, String msg) {
        response.setResponseCode(HttpResponseCode.NOT_FOUND);
        response.writeBody(msg);
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