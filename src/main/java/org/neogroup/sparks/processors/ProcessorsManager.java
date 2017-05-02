
package org.neogroup.sparks.processors;

import org.neogroup.sparks.ApplicationContext;
import org.neogroup.sparks.Manager;
import org.neogroup.sparks.commands.Command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProcessorsManager extends Manager {

    private final Map<Class<? extends Processor>, Processor> processors;
    private final Map<Class<? extends Command>, Processor> processorsByCommand;

    public ProcessorsManager(ApplicationContext applicationContext) {

        super(applicationContext);
        this.processors = new HashMap<>();
        this.processorsByCommand = new HashMap<>();
    }

    @Override
    public void onStart() {
        for (Processor processor : processors.values()) {
            processor.onStart();
        }
    }

    @Override
    public void onStop() {
        for (Processor processor : processors.values()) {
            processor.onStop();
        }
    }

    public Collection<Processor> getRegisteredProcessors () {
        return processors.values();
    }

    public Processor getProcessor (Class<? extends Command> commandClass) {
        return processorsByCommand.get(commandClass);
    }

    public final void registerProcessor (Class<? extends Processor> processorClass) {
        if (!processors.containsKey(processorClass)) {
            try {
                //Obtener una instancia del procesador
                Processor processor = processorClass.newInstance();
                processor.setApplicationContext(applicationContext);
                processors.put(processorClass, processor);

                //Asociar este procesador a los comandos especificados para este procesador
                ProcessorComponent processorAnnotation = processorClass.getAnnotation(ProcessorComponent.class);
                if (processorAnnotation != null) {
                    Class<? extends Command>[] commandClasses = processorAnnotation.commands();
                    for (Class<? extends Command> commandClass : commandClasses) {
                        processorsByCommand.put(commandClass, processor);
                    }
                }
            } catch (Exception exception) {
                throw new ProcessorException("Error registering processor", exception);
            }
        }
    }

    public final void registerProcessors (Class<? extends Processor> ... processorClasses) {
        for (Class<? extends Processor> processorClass : processorClasses) {
            registerProcessor(processorClass);
        }
    }

    public final <R extends Object> R processCommand(Command command) {
        Processor processor = getProcessor(command.getClass());
        if (processor == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return (R) processor.process(command);
    }
}
