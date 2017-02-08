
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.*;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class ApplicationContext {

    protected boolean running;
    protected Properties properties;
    protected Logger logger;
    protected Translator translator;
    private final Map<Class<? extends Processor>, Processor> processors;
    private final Map<Class<? extends Command>, Processor> processorsByCommand;

    public ApplicationContext() {
        running = false;
        this.processors = new HashMap<>();
        this.processorsByCommand = new HashMap<>();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Translator getTranslator() {
        return translator;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
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
                processor.setApplicationContext(this);
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

    public final <R extends Object> R processCommand(Command command) {
        Processor processor = getProcessor(command.getClass());
        if (processor == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return (R) processor.process(command);
    }

    public final void start () {
        if (!running) {
            for (Processor processor : processors.values()) {
                processor.onStart();
            }
            onStart();
            running = true;
        }
    }

    public final void stop () {
        if (running) {
            for (Processor processor : processors.values()) {
                processor.onStop();
            }
            onStop();
            running = false;
        }
    }

    protected abstract void onStart ();
    protected abstract void onStop ();
}
