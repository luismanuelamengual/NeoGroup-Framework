
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public abstract class Module extends ApplicationContext {

    protected Application application;

    public Module() {
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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
    public Collection<Processor> getRegisteredProcessors() {
        List<Processor> registeredProcessors = new ArrayList<>();
        registeredProcessors.addAll(super.getRegisteredProcessors());
        registeredProcessors.addAll(application.getRegisteredProcessors());
        return registeredProcessors;
    }

    @Override
    public <R extends Object> R processCommand(Command command) {

        R response = null;
        try {
            response = super.processCommand(command);
        }
        catch (ProcessorNotFoundException exception) {
            response = application.processCommand(command);
        }
        return (R) response;
    }
}
