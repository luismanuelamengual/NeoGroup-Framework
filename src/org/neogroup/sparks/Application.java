
package org.neogroup.sparks;

import org.neogroup.sparks.processors.Processor;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME = "localization/messages";
    private final static String LOGGER_NAME = "sparks_logger";
    private final static String DEFAULT_LOGGER_BUNDLE_NAME_PROPERTY = "loggerBundleName";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME_PROPERTY = "messagesBundleName";

    private final Map<Class<? extends Processor>, Processor> controllers;
    private final Properties properties;
    private final Logger logger;
    private final Translator translator;

    public Application () {

        controllers = new HashMap<>();

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

        //Traductor de la aplicación
        String defaultBundleResourceName = properties.get(DEFAULT_MESSAGES_BUNDLE_NAME_PROPERTY);
        if (defaultBundleResourceName == null) {
            defaultBundleResourceName = DEFAULT_MESSAGES_BUNDLE_NAME;
        }
        translator = new Translator();
        translator.setDefaultBundleName(defaultBundleResourceName);
    }

    public final void registerController(Class<? extends Processor> controllerClass) {

        try {
            Processor controller = controllerClass.newInstance();
            controller.setApplication(this);
            controllers.put(controllerClass, controller);
            getLogger().log(Level.INFO,"Processor \"{0}\" registered !!", controllerClass.getName());
        }
        catch (Throwable ex) {
            throw new RuntimeException("Error registering processors \"" + controllerClass + "\"", ex);
        }
    }

    public final void registerComponents () {

        /*
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
        */
    }

    public final Properties getProperties() {
        return properties;
    }

    public final Logger getLogger() {
        return logger;
    }

    public final Translator getTranslator() {
        return translator;
    }
}