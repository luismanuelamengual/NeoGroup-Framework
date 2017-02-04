
package org.neogroup.sparks.processors;

import org.neogroup.sparks.Application;
import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.commands.CommandComponent;
import org.neogroup.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

public class ProcessorFactory {

    private final Application application;
    private final Map<Class<? extends Command>, ProcessorSelector> selectors;
    private final Map<Class<? extends Processor>, Processor> processors;

    public ProcessorFactory(Application application) {
        this.application = application;
        selectors = new HashMap<>();
        processors = new HashMap<>();
    }

    public void registerProcessor (Class<? extends Processor> processorClass) {
        try {
            ProcessorComponent processorAnnotation = ReflectionUtils.findAnnotation(processorClass, ProcessorComponent.class);
            if (processorAnnotation != null) {
                Class<? extends Command>[] commandClasses = processorAnnotation.commands();
                for (Class<? extends Command> commandClass : commandClasses) {

                    ProcessorSelector selector = selectors.get(commandClass);
                    if (selector == null) {
                        CommandComponent commandComponent = commandClass.getAnnotation(CommandComponent.class);
                        if (commandComponent != null) {
                            selector = commandComponent.selector().newInstance();
                        } else {
                            selector = new DefaultProcessorSelector();
                        }
                        selectors.put(commandClass, selector);
                    }

                    selector.addProcessorCandidate(processorClass);
                }

                if (processorAnnotation.singleInstance()) {
                    Processor processor = processorClass.newInstance();
                    processor.setApplication(application);
                    processors.put(processorClass, processor);
                }
            }
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Error registering processor", throwable);
        }
    }

    public Processor getProcessor (Command command) {
        Class<? extends Processor> processorClass = selectors.get(command.getClass()).getProcessorClass(command);
        Processor processor = processors.get(processorClass);
        if (processor == null) {
            try {
                processor = processorClass.newInstance();
                processor.setApplication(application);
            } catch (Exception ex) {}
        }
        return processor;
    }
}
