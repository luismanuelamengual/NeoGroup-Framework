package org.neogroup.websparks;

public abstract class Processor<C extends Command> {

    private Application application;

    public final Application getApplication() {
        return application;
    }

    public final void setApplication(Application application) {
        this.application = application;
    }

    protected Object executeAction(Command commmand) {
        return application.executeCommand(commmand);
    }

    public abstract Object execute (C command);
}