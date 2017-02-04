
package org.neogroup.sparks;

import org.neogroup.sparks.commands.HelloWorldCommand;
import org.neogroup.sparks.processors.HelloWorldProcessor;

public class Main {

    public static void main(String[] args) {

        Application application = new Application();
        application.registerProcessor(HelloWorldProcessor.class);
        application.executeCommand(new HelloWorldCommand("pedro"));
    }
}