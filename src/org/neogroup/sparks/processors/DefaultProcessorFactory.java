
package org.neogroup.sparks.processors;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.commands.CommandComponent;
import org.neogroup.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

public class DefaultProcessorFactory extends ProcessorFactory<Processor, Command> {

    private final ApplicationContext applicationContext;
    private final Map<Class<? extends Command>, ProcessorSelector> selectors;
    private final Map<Class<? extends Processor>, Processor> processors;

    public DefaultProcessorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        selectors = new HashMap<>();
        processors = new HashMap<>();
    }

    @Override
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
                    processor.setApplicationContext(applicationContext);
                    processors.put(processorClass, processor);
                }
            }
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Error registering processor", throwable);
        }
    }

    @Override
    public void unregisterProcessor (Class<? extends Processor> processorClass) {
        //TODO: Unregister a processor class
    }

    @Override
    public Processor getProcessor (Command command) {
        Processor processor = null;
        ProcessorSelector selector = selectors.get(command.getClass());
        if (selector != null) {
            Class<? extends Processor> processorClass = selector.getProcessorClass(command);
            processor = processors.get(processorClass);
            if (processor == null) {
                try {
                    processor = processorClass.newInstance();
                    processor.setApplicationContext(applicationContext);
                } catch (Exception ex) {
                }
            }
        }
        return processor;
    }
}
