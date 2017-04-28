
package org.neogroup.sparks.console.processors;

import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.processors.ProcessorComponent;
import org.neogroup.sparks.processors.SelectorProcessor;

import java.util.HashMap;
import java.util.Map;

@ProcessorComponent(commands = {ConsoleCommand.class})
public class ConsoleSelectorProcessor extends SelectorProcessor<ConsoleCommand, ConsoleProcessor> {

    private final Map<String, ConsoleProcessor> processorsByCommandName;

    public ConsoleSelectorProcessor() {
        this.processorsByCommandName = new HashMap<>();
    }

    @Override
    public boolean registerProcessorCandidate(ConsoleProcessor processor) {
        boolean registered = false;
        ProcessCommands processCommands = processor.getClass().getAnnotation(ProcessCommands.class);
        if (processCommands != null) {
            String[] commands = processCommands.value();
            for (String command : commands) {
                processorsByCommandName.put(command, processor);
            }
            registered = true;
        }
        return registered;
    }

    @Override
    public ConsoleProcessor getProcessor(ConsoleCommand command) {
        return processorsByCommandName.get(command.getCommand().getName());
    }
}
