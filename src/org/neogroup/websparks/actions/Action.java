
package org.neogroup.websparks.actions;

import org.neogroup.websparks.Application;

public abstract class Action {

    protected final Application application;

    public Action(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public abstract void execute () throws Exception;
}