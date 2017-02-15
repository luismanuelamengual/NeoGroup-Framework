
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.templating.TemplatesManager;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public abstract class Module extends ApplicationContext {

    protected final Application application;

    public Module(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    @Override
    public Properties getProperties() {
        Properties properties = super.getProperties();
        if (properties == null) {
            properties = application.getProperties();
        }
        return properties;
    }

    @Override
    public Logger getLogger() {
        Logger logger = super.getLogger();
        if (logger == null) {
            logger = application.getLogger();
        }
        return logger;
    }

    @Override
    public Translator getTranslator() {
        Translator translator = super.getTranslator();
        if (translator == null) {
            translator = application.getTranslator();
        }
        return translator;
    }

    @Override
    public TemplatesManager getTemplatesManager() {
        TemplatesManager templatesManager = super.getTemplatesManager();
        if (templatesManager == null) {
            templatesManager = application.getTemplatesManager();
        }
        return templatesManager;
    }

    @Override
    public Processor getProcessor(Class<? extends Command> commandClass) {
        Processor processor = super.getProcessor(commandClass);
        if (processor == null) {
            processor = application.getProcessor(commandClass);
        }
        return processor;
    }

    @Override
    public Collection<Processor> getRegisteredProcessors() {
        List<Processor> registeredProcessors = new ArrayList<>();
        registeredProcessors.addAll(super.getRegisteredProcessors());
        registeredProcessors.addAll(application.getRegisteredProcessors());
        return registeredProcessors;
    }
}
