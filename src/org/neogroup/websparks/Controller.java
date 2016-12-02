package org.neogroup.websparks;

public abstract class Controller<C extends Command> {

    private Application application;

    public final Application getApplication() {
        return application;
    }

    public final void setApplication(Application application) {
        this.application = application;
    }

    protected Object executeCommand (Command commmand) {
        return application.executeCommand(commmand);
    }

    public abstract Object execute (C command);
}