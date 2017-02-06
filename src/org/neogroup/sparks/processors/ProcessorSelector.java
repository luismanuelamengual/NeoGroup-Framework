
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

public abstract class ProcessorSelector<C extends Command, P extends Processor> {

    public abstract void addProcessorCandidate(Class<? extends P> processorClass);
    public abstract void removeProcessorCandidate(Class<? extends P> processorClass);
    public abstract Class<? extends P> getProcessorClass(C command);
}
