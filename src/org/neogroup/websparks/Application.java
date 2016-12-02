
package org.neogroup.websparks;

import java.util.HashMap;
import java.util.Map;

public class Application {

    private final Map<Class<? extends Command>, Controller> controllers;

    public Application () {
        controllers = new HashMap<>();
    }

    public void registerController (Class<? extends Controller> controllerClass) {
        try {
            Controller controller = controllerClass.newInstance();
            controller.setApplication(this);
            registerController(controller);
        }
        catch (Throwable ex) {
            throw new RuntimeException("Error registering controller \"" + controllerClass + "\"");
        }
    }

    public Object executeCommand (Command command) {
        Controller controller = getController(command);
        return controller.execute(command);
    }

    protected void registerController (Controller controller) {

        ControllerComponent controllerAnnotation = controller.getClass().getAnnotation(ControllerComponent.class);
        if(controllerAnnotation != null){
            Class<? extends Command>[] commandClasses = controllerAnnotation.commands();
            for (Class<? extends Command> commandClass : commandClasses) {
                controllers.put(commandClass, controller);
            }
        }
    }

    protected Controller getController (Command command) {
        return controllers.get(command.getClass());
    }
}