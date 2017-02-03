
package org.neogroup.sparks.processors;

import org.neogroup.sparks.Application;
import org.neogroup.sparks.commands.Command;

public abstract class Processor <C extends Command, R extends Object> {

    private Application application;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public abstract R execute (C command);
}