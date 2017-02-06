
package org.neogroup.sparks;

import org.neogroup.sparks.processors.DefaultProcessorFactory;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Application extends ApplicationContext {

    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME = "localization/messages";
    private final static String LOGGER_NAME = "sparks_logger";
    private final static String DEFAULT_LOGGER_BUNDLE_NAME_PROPERTY = "logger_default_bundle_name";
    private final static String DEFAULT_MESSAGES_BUNDLE_NAME_PROPERTY = "messages_default_bundle_name";

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
        processorFactory = new DefaultProcessorFactory(this);

        //Modulos de la aplicación
        modules = new ArrayList<>();
    }

    public final void addModule(Module module) {
        module.setApplication(this);
        modules.add(module);
    }

    public final void removeModule(Module module) {
        module.setApplication(null);
        modules.remove(module);
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