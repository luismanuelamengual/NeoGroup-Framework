
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProcessorManager <C extends Command, P extends Processor> {

    private final Map<Class<? extends P>, P> processorInstances;
    private final List<Class<? extends P>> processors;

    public ProcessorManager() {
        processorInstances = new HashMap<>();
        processors = new ArrayList<>();
    }

    public final P getProcessor (C command) {

        P result = null;
        Class<? extends P> processorClass = selectProcessor(command);

        if(processorClass != null && processors.contains(processorClass)){

            ProcessorComponent annotation = processorClass.getAnnotation(ProcessorComponent.class);
            if(annotation.statefull()){
                result = processorInstances.get(processorClass);
                if(result == null){
                    try {
                        result = processorClass.newInstance();
                        processorInstances.put(processorClass, result);
                    } catch (Exception ex) {}
                }
            } else {
                try {
                    result = processorClass.newInstance();
                } catch (Exception ex) {}
            }
        }

        return result;
    }

    public List<Class<? extends P>> getProcessorClasses () {
        return processors;
    }

    public void addProcessorClass (Class<? extends P> processorClass) {
        processors.add(processorClass);
    }

    protected abstract Class<? extends P> selectProcessor (C command);
}
