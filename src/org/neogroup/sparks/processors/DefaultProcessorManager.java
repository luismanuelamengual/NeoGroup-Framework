
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

public class DefaultProcessorManager extends ProcessorManager<Command, Processor> {

    @Override
    protected Class<? extends Processor> selectProcessor(Command command) {
        return getProcessorClasses().get(0);
    }
}
