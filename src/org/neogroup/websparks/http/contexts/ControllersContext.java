
package org.neogroup.websparks.http.contexts;

import org.neogroup.websparks.Controller;
import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.HttpResponseCode;
import org.neogroup.websparks.routing.Route;
import org.neogroup.websparks.routing.RouteAction;
import org.neogroup.websparks.routing.RouteParam;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

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
                        String routeAction = routeActionAnnotation.value();
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
    public HttpResponse onContext (HttpRequest request) {
        
        String controllerPath = getControllerPath(request);
        Controller controller = controllers.get(controllerPath);
        HttpResponse response = null;
        if (controller == null) {
            response = onNotFound (request, "Controller with path \"" + controllerPath + "\" not found !!");
        }
        else {

            String controllerAction = getControllerAction(request);
            Method controllerMethod = controllerActions.get(controller).get(controllerAction);
            if (controllerMethod == null) {
                response = onNotFound (request, "Action \"" + controllerAction + "\" not found in controller \"" + controller.toString() + "\" !!");
            }
            else {
                try {
                    Object[] parameters = null;
                    Parameter[] controllerMethodParameters = controllerMethod.getParameters();
                    if (controllerMethodParameters.length > 0) {
                        parameters = new Object[controllerMethodParameters.length];
                        for (int i = 0; i < controllerMethodParameters.length; i++) {
                            Parameter controllerMethodParameter = controllerMethodParameters[i];
                            RouteParam routeParamAnnotation = controllerMethodParameter.getAnnotation(RouteParam.class);
                            if (routeParamAnnotation != null) {
                                String parameterName = routeParamAnnotation.value();
                                parameters[i] = request.getParameter(parameterName);
                            }
                            else {
                                parameters[i] = null;
                            }
                        }
                    }

                    controllerMethod.invoke(controller, parameters);
                    response = ContextInstance.getInstance().getResponse();
                }
                catch (Throwable ex) {
                    response = onError(request, ex instanceof InvocationTargetException? ex.getCause() : ex);
                }
            }
        }
        return response;
    }
    
    protected HttpResponse onNotFound (HttpRequest request, String msg) {
        HttpResponse response = new HttpResponse();
        response.setResponseCode(HttpResponseCode.NOT_FOUND);
        response.setBody(msg);
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