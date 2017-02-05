
package org.neogroup.sparks;

import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.logging.Logger;

public abstract class Module {

    protected Application application;
    protected boolean running;

    public Module(Application application) {
        this.application = application;
        running = false;
    }

    public Application getApplication() {
        return application;
    }

    public Properties getProperties() {
        return application.getProperties();
    }

    public Logger getLogger() {
        return application.getLogger();
    }

    public Translator getTranslator() {
        return application.getTranslator();
    }

    public void start () {
        if (!running) {
            onStart();
            running = true;
        }
    }

    public void stop () {
        if (running) {
            onStop();
            running = false;
        }
    }

    protected abstract void onStart ();
    protected abstract void onStop ();
}
