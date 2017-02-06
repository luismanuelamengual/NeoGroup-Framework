
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

public class DefaultProcessorSelector extends ProcessorSelector<Command,Processor> {

    private Class<? extends Processor> selectedProcessorClass;

    @Override
    public void addProcessorCandidate(Class<? extends Processor> processorClass) {
        selectedProcessorClass = processorClass;
    }

    @Override
    public void removeProcessorCandidate(Class<? extends Processor> processorClass) {
        //TODO: Remover una clase candidata
    }

    @Override
    public Class<? extends Processor> getProcessorClass(Command command) {
        return selectedProcessorClass;
    }
}
