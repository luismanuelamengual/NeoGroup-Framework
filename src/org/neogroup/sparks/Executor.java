package org.neogroup.sparks;

import org.neogroup.sparks.util.Properties;

import java.util.logging.Logger;

public abstract class Executor<C extends Action> {

    private Application application;

    public final Application getApplication() {
        return application;
    }

    public final void setApplication(Application application) {
        this.application = application;
    }

    protected Object executeAction(Action action) {
        return application.executeAction(action);
    }

    protected Properties getProperties() {
        return application.getProperties();
    }

    protected Logger getLogger() {
        return application.getLogger();
    }

    public abstract Object execute (C command);
}