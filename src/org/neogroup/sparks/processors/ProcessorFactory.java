
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

public abstract class ProcessorFactory<P extends Processor, C extends Command> {

    public abstract void registerProcessor (Class<? extends P> processorClass);
    public abstract void unregisterProcessor (Class<? extends P> processorClass);
    public abstract P getProcessor (C command);
}
