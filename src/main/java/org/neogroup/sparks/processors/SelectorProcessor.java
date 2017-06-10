
package org.neogroup.sparks.processors;

import org.neogroup.sparks.Application;
import org.neogroup.sparks.Module;
import org.neogroup.sparks.commands.Command;

import java.util.*;

/**
 * Processor that works as a proxy for other registered processors to be executed
 * @param <C> Command type
 * @param <P> Processors type
 */
public abstract class SelectorProcessor<C extends Command, P extends Processor>  extends Processor<C,Object> {

    /**
     * Initializes the processor
     */
    @Override
    public void initialize() {

        //Retrieve all processors visible from this module/application
        Set<Class<? extends Processor>> registeredProcessors = new HashSet<>();
        registeredProcessors.addAll(getApplicationContext().getRegisteredProcessors());
        if (getApplicationContext() instanceof Module) {
            Module module = (Module)getApplicationContext();
            registeredProcessors.addAll(module.getApplication().getRegisteredProcessors());
        }
        else if (getApplicationContext() instanceof Application) {
            Application application = (Application)getApplicationContext();
            for (Module module : application.getModules()) {
                registeredProcessors.addAll(module.getRegisteredProcessors());
            }
        }

        //Register processor class candidates
        for (Class<? extends Processor> processorClass : registeredProcessors) {
            try {
                Class<? extends P> castedProcessorClass = (Class<? extends P>) processorClass;
                registerProcessorClass(castedProcessorClass);
            }
            catch (ClassCastException ex) {}
        }
    }

    /**
     * Processes a command
     * @param command command to process
     * @return Objecte processor response
     * @throws ProcessorException
     */
    @Override
    public Object process(C command) throws ProcessorException {
        Class<? extends P> processorClass = getProcessorClass(command);
        if (processorClass == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return getApplicationContext().getProcessorInstance(processorClass).process(command);
    }

    /**
     * Method that is executed for all the processor registered that
     * match the processor class type
     * @param processorClass processor class
     * @return boolean should indicate if the processor was registered or not
     */
    protected abstract boolean registerProcessorClass (Class<? extends P> processorClass);

    /**
     * Get a processor class for a command
     * @param command command
     * @return class of a registered processor
     */
    protected abstract Class<? extends P> getProcessorClass (C command);
}
