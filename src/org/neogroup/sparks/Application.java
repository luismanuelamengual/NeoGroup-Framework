
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorFactory;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.logging.Logger;

public abstract class Application {

    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME = "localization/messages";
    private final static String LOGGER_NAME = "sparks_logger";
    private final static String DEFAULT_LOGGER_BUNDLE_NAME_PROPERTY = "loggerBundleName";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME_PROPERTY = "messagesBundleName";

    private final Properties properties;
    private final Logger logger;
    private final Translator translator;
    private final ProcessorFactory processorFactory;

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

        //Fabrica de Procesadores
        processorFactory = new ProcessorFactory(this);
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
        processorFactory.registerProcessor(processorClass);
    }

    public final <R extends Object> R executeCommand (Command command) {
        Processor processor = processorFactory.getProcessor(command);
        return (R)processor.processCommand(command);
    }

    public abstract void start ();
    public abstract void stop ();
}