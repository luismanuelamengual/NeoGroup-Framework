
package org.neogroup.websparks;

import org.neogroup.websparks.util.Properties;
import org.neogroup.websparks.util.Scanner;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.*;

public class Application {

    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String LOGGER_NAME = "websparks";
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

    public void registerExecutor(Class<? extends Executor> executorClass) {
        try {
            Executor executor = executorClass.newInstance();
            executor.setApplication(this);
            registerExecutor(executor);
        }
        catch (Throwable ex) {
            throw new RuntimeException("Error registering executor \"" + executorClass + "\"", ex);
        }
    }

    public void registerComponents () {

        Scanner controllersScanner = new Scanner();
        Set<Class> executorClasses = controllersScanner.findClasses(new Scanner.ClassFilter() {
            @Override
            public boolean accept(Class clazz) {
                return Executor.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers());
            }
        });
        for (Class executorClass : executorClasses) {
            registerExecutor(executorClass);
        }
    }

    public Object executeAction(Action action) {
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

    public Properties getProperties() {
        return properties;
    }

    public Logger getLogger() {
        return logger;
    }

    protected void registerExecutor(Executor processor) {

        ExecutorComponent controllerAnnotation = processor.getClass().getAnnotation(ExecutorComponent.class);
        if(controllerAnnotation != null){
            Class<? extends Action>[] commandClasses = controllerAnnotation.commands();
            for (Class<? extends Action> commandClass : commandClasses) {
                executors.put(commandClass, processor);
            }
        }
    }

    protected Executor getExecutor(Action action) {
        return executors.get(action.getClass());
    }

    protected Object onActionError(Action action, Throwable throwable) {
        return null;
    }
}