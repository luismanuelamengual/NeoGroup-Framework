
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

/**
 * Processor of commands for the sparks framework
 * @param <C> Command type
 */
public abstract class CommandProcessor<C extends Command> extends Processor {

    /**
     * Processes a command
     * @param command command to process
     * @return response
     * @throws ProcessorException
     */
    public abstract Object process (C command) throws ProcessorException;
}
