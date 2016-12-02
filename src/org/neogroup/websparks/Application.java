
package org.neogroup.websparks;

import java.util.HashMap;
import java.util.Map;

public class Application {

    private final Map<Class<? extends Command>, Processor> processors;

    public Application () {
        processors = new HashMap<>();
    }

    public void registerProcessor(Class<? extends Processor> processorClass) {
        try {
            Processor controller = processorClass.newInstance();
            controller.setApplication(this);
            registerProcessor(controller);
        }
        catch (Throwable ex) {
            throw new RuntimeException("Error registering controller \"" + processorClass + "\"");
        }
    }

    public Object executeCommand (Command command) {
        Processor controller = getController(command);
        return controller.execute(command);
    }

    protected void registerProcessor(Processor processor) {

        ProcessorComponent controllerAnnotation = processor.getClass().getAnnotation(ProcessorComponent.class);
        if(controllerAnnotation != null){
            Class<? extends Command>[] commandClasses = controllerAnnotation.commands();
            for (Class<? extends Command> commandClass : commandClasses) {
                processors.put(commandClass, processor);
            }
        }
    }

    protected Processor getController (Command command) {
        return processors.get(command.getClass());
    }
}