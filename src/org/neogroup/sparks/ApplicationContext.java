
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorFactory;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.logging.Logger;

public abstract class ApplicationContext {

    protected Properties properties;
    protected Logger logger;
    protected Translator translator;
    protected ProcessorFactory processorFactory;

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

    public ProcessorFactory getProcessorFactory() {
        return processorFactory;
    }

    public void setProcessorFactory(ProcessorFactory processorFactory) {
        this.processorFactory = processorFactory;
    }

    public void registerProcessor (Class<? extends Processor> processorClass) {
        processorFactory.registerProcessor(processorClass);
    }

    public void unregisterProcessor (Class<? extends Processor> processorClass) {
        processorFactory.unregisterProcessor(processorClass);
    }

    public Processor getProcessor (Command command) {
        return processorFactory.getProcessor(command);
    }

    public <R extends Object> R processCommand(Command command) {
        Processor processor = getProcessor(command);
        if (processor == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return (R) processor.process(command);
    }
}
