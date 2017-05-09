
package org.neogroup.sparks;

import org.neogroup.sparks.bundles.BundlesManager;
import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.data.DataSourcesManager;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorsManager;
import org.neogroup.sparks.properties.Properties;
import org.neogroup.sparks.views.View;
import org.neogroup.sparks.views.ViewException;
import org.neogroup.sparks.views.ViewFactory;
import org.neogroup.sparks.views.ViewsManager;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.logging.Logger;

public abstract class ApplicationContext {

    protected boolean running;
    protected Properties properties;
    protected Logger logger;
    protected final BundlesManager bundlesManager;
    protected final ViewsManager viewsManager;
    protected final ProcessorsManager processorsManager;
    protected final DataSourcesManager dataSourcesManager;

    public ApplicationContext() {
        running = false;
        bundlesManager = new BundlesManager(this);
        viewsManager = new ViewsManager(this);
        processorsManager = new ProcessorsManager(this);
        dataSourcesManager = new DataSourcesManager(this);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public final void start () {
        if (!running) {
            bundlesManager.onStart();
            viewsManager.onStart();
            processorsManager.onStart();
            dataSourcesManager.onStart();
            onStart();
            running = true;
        }
    }

    public final void stop () {
        if (running) {
            bundlesManager.onStop();
            viewsManager.onStop();
            processorsManager.onStop();
            dataSourcesManager.onStop();
            onStop();
            running = false;
        }
    }

    public void registerProcessor(Class<? extends Processor> processorClass) {
        processorsManager.registerProcessor(processorClass);
    }

    public void registerProcessors(Class<? extends Processor> ... processorClasses) {
        processorsManager.registerProcessors(processorClasses);
    }

    public Collection<Processor> getRegisteredProcessors() {
        return processorsManager.getRegisteredProcessors();
    }

    public <R> R processCommand(Command command) {
        return processorsManager.processCommand(command);
    }

    public void addViewFactory(String viewFactoryName, ViewFactory viewFactory) {
        viewsManager.addViewFactory(viewFactoryName, viewFactory);
    }

    public void removeViewFactory(String viewFactoryName) {
        viewsManager.removeViewFactory(viewFactoryName);
    }

    public View createView(String viewName) throws ViewException {
        return viewsManager.createView(viewName);
    }

    public View createView(String viewFactoryName, String viewName) throws ViewException {
        return viewsManager.createView(viewFactoryName, viewName);
    }

    public void addDataSource(String dataSourceName, DataSource dataSource) {
        dataSourcesManager.addDataSource(dataSourceName, dataSource);
    }

    public void removeDataSource(String name) {
        dataSourcesManager.removeDataSource(name);
    }

    public DataSource getDataSource() {
        return dataSourcesManager.getDataSource();
    }

    public DataSource getDataSource(String name) {
        return dataSourcesManager.getDataSource(name);
    }

    protected abstract void onStart ();
    protected abstract void onStop ();
}
