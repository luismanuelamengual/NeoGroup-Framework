package org.neogroup.websparks;

public abstract class Executor<C extends Action> {

    private Application application;

    public final Application getApplication() {
        return application;
    }

    public final void setApplication(Application application) {
        this.application = application;
    }

    protected Object executeAction(Action commmand) {
        return application.executeAction(commmand);
    }

    public abstract Object execute (C command);
}