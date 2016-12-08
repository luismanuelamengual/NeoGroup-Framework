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

    public String getString(String key, Object... args) {
        return application.getString(key, args);
    }

    public String getString(String key, Locale locale, Object... args) {
        return application.getString(key, locale, args);
    }

    public String getBundleString(String bundleName, String key, Object... args) {
        return application.getBundleString(bundleName, key, args);
    }

    public String getBundleString(String bundleName, String key, Locale locale, Object... args) {
        return application.getBundleString(bundleName, key, locale, args);
    }

    public abstract Object execute (A action);
}