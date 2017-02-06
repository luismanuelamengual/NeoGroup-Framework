
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

public abstract class ProcessorFactory {

    public abstract void registerProcessor (Class<? extends Processor> processorClass);
    public abstract void unregisterProcessor (Class<? extends Processor> processorClass);
    public abstract Processor getProcessor (Command command);
}
