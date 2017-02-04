
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.HelloWorldCommand;

@ProcessorComponent(commands = {HelloWorldCommand.class})
public class HelloWorldProcessor extends Processor<HelloWorldCommand, Boolean> {

    @Override
    public Boolean processCommand(HelloWorldCommand command) {

        System.out.println ("Hi " + command.getName() + ", hello world");
        return true;
    }
}
