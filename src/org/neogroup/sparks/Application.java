
package org.neogroup.sparks;

import org.neogroup.sparks.util.Properties;
import org.neogroup.sparks.util.Scanner;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.*;

public class Application {

    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String LOGGER_NAME = "sparks";
    private final static String LOGGER_PROPERTIES_RESOURCE_NAME_PROPERTY = "loggerPropertiesReourceName";

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
        String loggerResourceName = properties.get(LOGGER_PROPERTIES_RESOURCE_NAME_PROPERTY);
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