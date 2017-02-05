
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorFactory;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Application {

    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME = "localization/messages";
    private final static String LOGGER_NAME = "sparks_logger";
    private final static String DEFAULT_LOGGER_BUNDLE_NAME_PROPERTY = "logger_default_bundle_name";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME_PROPERTY = "messages_default_bundle_name";

    protected final Properties properties;
    protected final Logger logger;
    protected final Translator translator;
    protected final ProcessorFactory processorFactory;
    protected final List<Module> modules;

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

        //Modulos de la aplicación
        modules = new ArrayList<>();
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

    public final void registerModule (Class<? extends Module> moduleClass) {
        try {
            Module module = moduleClass.getDeclaredConstructor(Application.class).newInstance(this);
            modules.add(module);
        }
        catch (Throwable throwable) {
            throw new RuntimeException("Module \"" + moduleClass.getName() + "\" couldnt be created !!");
        }
    }

    public final void registerProcessor (Class<? extends Processor> processorClass) {
        processorFactory.registerProcessor(processorClass);
    }

    public final <R extends Object> R executeCommand (Command command) {
        Processor processor = processorFactory.getProcessor(command);
        if (processor == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return (R) processor.processCommand(command);
    }

    public void start () {
        for (Module module : modules) {
            module.start();
        }
    }

    public void stop () {
        for (Module module : modules) {
            module.stop();
        }
    }
}