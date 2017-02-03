
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorComponent;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Application {

    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME = "localization/messages";
    private final static String LOGGER_NAME = "sparks_logger";
    private final static String DEFAULT_LOGGER_BUNDLE_NAME_PROPERTY = "loggerBundleName";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME_PROPERTY = "messagesBundleName";

    private final Properties properties;
    private final Logger logger;
    private final Translator translator;

    private final Map<Class<? extends Processor>, Processor> processors;

    public Application () {

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

        //Procesadores
        processors = new HashMap<>();
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

    public final void registerProcessor (Class<? extends Processor> processorClass) {

        if (!Modifier.isAbstract(processorClass.getModifiers())) {

            Class<? extends Command>[] commandClasses = null;
            boolean statefullProcessor = true;

            ProcessorComponent processorComponentAnnotation = processorClass.getAnnotation(ProcessorComponent.class);
            if (processorComponentAnnotation != null) {
                commandClasses = processorComponentAnnotation.commands();
                statefullProcessor = processorComponentAnnotation.statefull();
            }

            if (commandClasses == null || commandClasses.length == 0) {
                Class<? extends Processor> currentProcessorClass = processorClass;
                do {
                    currentProcessorClass = (Class<? extends Processor>) currentProcessorClass.getSuperclass();
                    ProcessorComponent currentProcessorComponentAnnotation = currentProcessorClass.getAnnotation(ProcessorComponent.class);
                    if (currentProcessorComponentAnnotation != null) {
                        commandClasses = currentProcessorComponentAnnotation.commands();
                    }
                } while (commandClasses == null || commandClasses.length == 0);
            }

            if (commandClasses != null && commandClasses.length > 0) {
                for (Class<? extends Command> commandClass : commandClasses) {

                }
            }
        }
        return;
    }

    public final Object executeCommand (Command command) {
        return null;
    }
}