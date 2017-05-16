
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
import org.neogroup.sparks.views.View;
import org.neogroup.sparks.views.ViewException;
import org.neogroup.sparks.views.ViewNotFoundException;

import javax.sql.DataSource;
import java.util.Locale;

/**
 * Module of a sparks
 */
public abstract class Module extends ApplicationContext {

    protected final Application application;

    /**
     * Default constructor of a module
     * @param application
     */
    public Module(Application application) {
        this.application = application;
    }

    /**
     * Get the application of the module
     * @return Application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Get a bundle string
     * @param bundleName name of bundle
     * @param locale Locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String bundle string
     */
    @Override
    public String getBundleString(String bundleName, Locale locale, String key, Object... args) {
        String value = super.getBundleString(bundleName, locale, key, args);
        if (value == null) {
            value = application.getBundleString(bundleName, locale, key, args);
        }
        return value;
    }

    /**
     * Get a property value
     * @param property name of the property
     * @param <R> casted response
     * @return R property value
     */
    @Override
    public <R> R getProperty(String property) {
        Object value = super.getProperty(property);
        if (value == null) {
            value = application.getProperty(property);
        }
        return (R)value;
    }

    /**
     * Indicates if a property exists
     * @param property name of the property
     * @return boolean
     */
    @Override
    public boolean hasProperty(String property) {
        boolean hasProperty = super.hasProperty(property);
        if (!hasProperty) {
            hasProperty = application.hasProperty(property);
        }
        return hasProperty;
    }

    /**
     * Get a processor instance
     * @param processorClass processor class
     * @return Processor
     */
    @Override
    public Processor getProcessorInstance(Class<? extends Processor> processorClass) {
        Processor processor = super.getProcessorInstance(processorClass);
        if (processor == null) {
            processor = application.getProcessorInstance(processorClass);
        }
        return processor;
    }

    /**
     * Process a command
     * @param command command to process
     * @param <R> casted response
     * @return R response
     */
    @Override
    public <R> R processCommand(Command command) {
        R response = null;
        try {
            response = super.processCommand(command);
        }
        catch (ProcessorNotFoundException notFoundExeption) {
            response = application.processCommand(command);
        }
        return response;
    }

    /**
     * Creates a view with a given name
     * @param viewFactoryName name of a view factory
     * @param viewName name of the view
     * @return View created view
     * @throws ViewException
     */
    @Override
    public View createView(String viewFactoryName, String viewName) throws ViewException {
        View view = null;
        try {
            view = super.createView(viewFactoryName, viewName);
        }
        catch (ViewNotFoundException notFoundExeption) {
            view = application.createView(viewFactoryName, viewName);
        }
        return view;
    }

    /**
     * Get a data source
     * @param dataSourceName name of the data source
     * @return DataSource data source
     */
    @Override
    public DataSource getDataSource(String dataSourceName) {
        DataSource dataSource = super.getDataSource(dataSourceName);
        if (dataSource == null) {
            dataSource = application.getDataSource(dataSourceName);
        }
        return dataSource;
    }
}
