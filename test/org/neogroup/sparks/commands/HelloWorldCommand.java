package org.neogroup.sparks.commands;

public class HelloWorldCommand extends Command {

    private String name;

    public HelloWorldCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
