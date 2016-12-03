
package org.neogroup.websparks;

import java.util.HashMap;
import java.util.Map;

public class Application {

    private final Map<Class<? extends Command>, Executor> processors;

    public Application () {
        processors = new HashMap<>();
    }

    public void registerExecutor(Class<? extends Executor> executorClass) {
        try {
            Executor executor = executorClass.newInstance();
            executor.setApplication(this);
            registerExecutor(executor);
        }
        catch (Throwable ex) {
            throw new RuntimeException("Error registering executor \"" + executorClass + "\"");
        }
    }

    public Object executeCommand (Command command) {
        Executor controller = getExecutor(command);
        return controller.execute(command);
    }

    protected void registerExecutor(Executor processor) {

        ExecutorComponent controllerAnnotation = processor.getClass().getAnnotation(ExecutorComponent.class);
        if(controllerAnnotation != null){
            Class<? extends Command>[] commandClasses = controllerAnnotation.commands();
            for (Class<? extends Command> commandClass : commandClasses) {
                processors.put(commandClass, processor);
            }
        }
    }

    protected Executor getExecutor(Command command) {
        return processors.get(command.getClass());
    }
}