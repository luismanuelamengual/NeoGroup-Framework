
package org.neogroup.sparks;

import org.neogroup.sparks.util.Properties;
import org.neogroup.sparks.util.Scanner;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.*;

public class Application {

    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME = "messages";
    private final static String LOGGER_NAME = "sparks_logger";
    private final static String DEFAULT_LOGGER_BUNDLE_NAME_PROPERTY = "loggerBundleName";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME_PROPERTY = "messagesBundleName";

    private final Map<Class<? extends Action>, Executor> executors;
    private final Properties properties;
    private final Logger logger;

    public Application () {

        executors = new HashMap<>();

        //Propiedades de la aplicación
        properties = new Properties();
        try {
            properties.loadResource(PROPERTIES_RESOURCE_NAME);
        }
        catch (Exception ex) {}

        //Logger de la aplicación
        String loggerResourceName = properties.get(DEFAULT_LOGGER_BUNDLE_NAME_PROPERTY);
        if (loggerResourceName != null) {
            logger = Logger.getLogger(LOGGER_NAME, loggerResourceName);
        }
        else {
            logger = Logger.getLogger(LOGGER_NAME);
        }
    }

    public final void registerExecutor(Class<? extends Executor> executorClass) {

        try {
            Executor executor = executorClass.newInstance();
            executor.setApplication(this);
            registerExecutor(executor);
            getLogger().log(Level.INFO,"Executor \"{0}\" registered !!", executorClass.getName());
        }
        catch (Throwable ex) {
            throw new RuntimeException("Error registering executor \"" + executorClass + "\"", ex);
        }
    }

    public final void registerComponents () {

        getLogger().log(Level.INFO,"Scanning classpaths components ...");
        Scanner controllersScanner = new Scanner();
        Set<Class> executorClasses = controllersScanner.findClasses(new Scanner.ClassFilter() {
            @Override
            public boolean accept(Class clazz) {
                return Executor.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers());
            }
        });
        getLogger().log(Level.INFO,"Registering executors ...");
        for (Class executorClass : executorClasses) {
            registerExecutor(executorClass);
        }
    }

    public final Object executeAction(Action action) {
        Object response = null;
        Executor executor = getExecutor(action);
        try {
            if (executor == null) {
                throw new ExecutorNotFoundException();
            }
            response = executor.execute(action);
        }
        catch (Throwable throwable) {
            response = onActionError(action, throwable);
        }
        return response;
    }

    public final Properties getProperties() {
        return properties;
    }

    public final Logger getLogger() {
        return logger;
    }

    public String getString (String key) {
        return getString(key, Locale.getDefault());
    }

    public String getString (String key, Locale locale) {
        String bundleName = properties.get(DEFAULT_MESSAGES_BUNDLE_NAME_PROPERTY);
        if (bundleName == null) {
            bundleName = DEFAULT_MESSAGES_BUNDLE_NAME;
        }
        return ResourceBundle.getBundle(bundleName, locale).getString(key);
    }

    public String getString (String bundleName, String key) {
        return getString(bundleName, key, Locale.getDefault());
    }

    public String getString (String bundleName, String key, Locale locale) {
        return ResourceBundle.getBundle(bundleName, locale).getString(key);
    }

    protected void registerExecutor(Executor executor) {

        ExecutorComponent controllerAnnotation = executor.getClass().getAnnotation(ExecutorComponent.class);
        if(controllerAnnotation != null){
            Class<? extends Action>[] actionClasses = controllerAnnotation.commands();
            for (Class<? extends Action> commandClass : actionClasses) {
                executors.put(commandClass, executor);
            }
        }
    }

    protected Executor getExecutor(Action action) {
        return executors.get(action.getClass());
    }

    protected Object onActionError(Action action, Throwable throwable) {
        getLogger().log(Level.WARNING, "Action error", throwable);
        return null;
    }
}