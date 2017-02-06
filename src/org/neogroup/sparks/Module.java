
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorFactory;
import org.neogroup.util.Properties;
import org.neogroup.util.Translator;

import java.util.logging.Logger;

public abstract class Module extends ApplicationContext {

    protected Application application;
    protected boolean running;

    public Module() {
        running = false;
        processorFactory = new ProcessorFactory(this);
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
    public Processor getProcessor(Command command) {
        Processor processor = super.getProcessor(command);
        if (processor == null) {
            processor = application.getProcessor(command);
        }
        return processor;
    }

    public void start () {
        if (!running) {
            onStart();
            running = true;
        }
    }

    public void stop () {
        if (running) {
            onStop();
            running = false;
        }
    }

    protected abstract void onStart ();
    protected abstract void onStop ();
}
