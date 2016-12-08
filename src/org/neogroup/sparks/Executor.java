package org.neogroup.sparks;

import org.neogroup.sparks.util.Properties;

import java.util.Locale;
import java.util.logging.Logger;

public abstract class Executor<A extends Action> {

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

    protected String getString(String key) {
        return application.getString(key);
    }

    protected String getString(String key, Locale locale) {
        return application.getString(key, locale);
    }

    protected String getString(String bundleName, String key) {
        return application.getString(bundleName, key);
    }

    protected String getString(String bundleName, String key, Locale locale) {
        return application.getString(bundleName, key, locale);
    }

    public abstract Object execute (A action);
}