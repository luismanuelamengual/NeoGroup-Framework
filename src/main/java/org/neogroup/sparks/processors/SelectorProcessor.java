
package org.neogroup.sparks.processors;

import org.neogroup.sparks.Module;
import org.neogroup.sparks.commands.Command;

import java.util.*;

public abstract class SelectorProcessor<C extends Command, P extends Processor>  extends Processor<C,Object> {

    @Override
    public void initialize() {

        //Retrieve all processors visible from this module/application
        Set<Class<? extends Processor>> registeredProcessors = new HashSet<>();
        registeredProcessors.addAll(getApplicationContext().getRegisteredProcessors());
        if (getApplicationContext() instanceof Module) {
            Module module = (Module)getApplicationContext();
            registeredProcessors.addAll(module.getApplication().getRegisteredProcessors());
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

    @Override
    public Object process(C command) throws ProcessorException {
        Class<? extends P> processorClass = getProcessorClass(command);
        if (processorClass == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return getApplicationContext().getProcessorInstance(processorClass).process(command);
    }

    protected abstract boolean registerProcessorClass (Class<? extends P> processorClass);
    protected abstract Class<? extends P> getProcessorClass (C command);
}
