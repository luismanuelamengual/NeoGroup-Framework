
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

public abstract class SelectorProcessor<C extends Command, P extends Processor>  extends Processor<C,Object> {

    @Override
    public void onStart() {
        for (Processor processor : getApplicationContext().getRegisteredProcessors()) {
            try {
                P castedProcessor = (P) processor;
                registerProcessorCandidate(castedProcessor);
            } catch (ClassCastException exception) {}
        }
    }

    @Override
    public Object process(C command) throws ProcessorException {
        P processor = getProcessor(command);
        if (processor == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return processor.process(command);
    }

    public abstract boolean registerProcessorCandidate (P processor);
    public abstract P getProcessor (C command);
}
