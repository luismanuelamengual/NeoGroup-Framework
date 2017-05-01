
package org.neogroup.sparks;

import org.neogroup.sparks.bundles.BundlesManager;
import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.views.ViewsManager;

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
    public BundlesManager getBundlesManager() {
        BundlesManager bundlesManager = super.getBundlesManager();
        if (bundlesManager == null) {
            bundlesManager = application.getBundlesManager();
        }
        return bundlesManager;
    }

    @Override
    public ViewsManager getViewsManager() {
        ViewsManager viewsManager = super.getViewsManager();
        if (viewsManager == null) {
            viewsManager = application.getViewsManager();
        }
        return viewsManager;
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
