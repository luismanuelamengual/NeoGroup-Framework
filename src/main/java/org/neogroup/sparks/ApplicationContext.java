
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.*;
import org.neogroup.sparks.views.View;
import org.neogroup.sparks.views.ViewException;
import org.neogroup.sparks.views.ViewFactory;
import org.neogroup.sparks.views.ViewNotFoundException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Application context
 */
public abstract class ApplicationContext {

    private static final String DEFAULT_BUNDLE_NAME_PROPERTY_NAME = "defaultBundleName";
    private static final String DEFAULT_VIEW_FACTORY_PROPERTY_NAME = "defaultViewFactory";
    private static final String DEFAULT_DATA_SOURCE_PROPERTY_NAME = "defaultDataSource";

    protected boolean running;
    protected final Properties properties;
    protected final Logger logger;
    protected final Map<String, DataSource> dataSources;
    protected final Map<String, ViewFactory> viewFactories;
    protected final Set<Class<? extends Processor>> registeredProcessors;
    protected final Map<Class<? extends Command>, Class<? extends Processor>> processorsByCommand;
    protected final Map<Class<? extends Processor>, Processor> singleInstanceProcessors;

    /**
     * Default constructor for the application context
     */
    public ApplicationContext() {
        running = false;
        this.properties = new Properties();
        this.logger = Logger.getGlobal();
        this.dataSources = new HashMap<>();
        this.viewFactories = new HashMap<>();
        this.registeredProcessors = new HashSet<>();
        this.processorsByCommand = new HashMap<>();
        this.singleInstanceProcessors = new HashMap<>();
    }

    /**
     * Get the context logger
     * @return Logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Get the properties of the context
     * @return
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Get a property value
     * @param property name of the property
     * @param <R> casted type of response
     * @return value of the property
     */
    public <R> R getProperty(String property) {
        return (R)this.properties.get(property);
    }

    /**
     * Indicates if a property exists
     * @param property name of the property
     * @return boolean
     */
    public boolean hasProperty(String property) {
        return this.properties.containsKey(property);
    }

    /**
     * Set a property value
     * @param property name of the property
     * @param value value of the property
     */
    public void setProperty(String property, Object value) {
        this.properties.put(property, value);
    }

    /**
     * Load properties from a classpath resource
     * @param resourceName name of the resource
     * @throws IOException
     */
    public void loadPropertiesFromResource (String resourceName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            this.properties.load(in);
        }
        catch (IOException exception) {
            throw new RuntimeException("Error reading properties resource file", exception);
        }
    }

    /**
     * Load properties from a file
     * @param filename name of the file
     * @throws IOException
     */
    public void loadPropertiesFromFile (String filename) {
        try (FileInputStream in = new FileInputStream(filename)) {
            this.properties.load(in);
        }
        catch (IOException exception) {
            throw new RuntimeException("Error reading properties file", exception);
        }
    }

    /**
     * Get a bundle string with the default locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    public String getString (String key, Object... args) {
        return getString(Locale.getDefault(), key, args);
    }

    /**
     * Get a bundle string
     * @param locale Locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    public String getString (Locale locale, String key, Object... args) {
        String value = null;
        if (hasProperty(DEFAULT_BUNDLE_NAME_PROPERTY_NAME)) {
            value = getString(getProperty(DEFAULT_BUNDLE_NAME_PROPERTY_NAME), locale, key, args);
        }
        return value;
    }

    /**
     * Get a bundle string
     * @param bundleName name of bundle
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    public String getString (String bundleName, String key, Object... args) {
        return getString(bundleName, Locale.getDefault(), key, args);
    }

    /**
     * Get a bundle string
     * @param bundleName name of bundle
     * @param locale Locale
     * @param key key of the bundle
     * @param args replacement arguments
     * @return String value of the bundle
     */
    public String getString (String bundleName, Locale locale, String key, Object... args) {
        String value = null;
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        if (bundle != null && bundle.containsKey(key)) {
            value = MessageFormat.format(bundle.getString(key), args);
        }
        return value;
    }

    /**
     * Register processor classes
     * @param processorClasses processor classes
     */
    public final void registerProcessors (Class<? extends Processor> ... processorClasses) {
        for (Class<? extends Processor> processorClass : processorClasses) {
            registerProcessor(processorClass);
        }
    }

    /**
     * Register a processor class
     * @param processorClass processor class
     */
    public final void registerProcessor (Class<? extends Processor> processorClass) {
        registeredProcessors.add(processorClass);
    }

    /**
     * Get the classes of the registered processors
     * @return set of registered processor classes
     */
    public Set<Class<? extends Processor>> getRegisteredProcessors() {
        return registeredProcessors;
    }

    /**
     * Get an instance of a processor class
     * @param processorClass processor class
     * @return Processor
     */
    public Processor getProcessorInstance (Class<? extends Processor> processorClass) {
        Processor processor = singleInstanceProcessors.get(processorClass);
        if (processor == null) {
            if (registeredProcessors.contains(processorClass)) {
                try {
                    processor = processorClass.newInstance();
                    processor.setApplicationContext(this);
                    processor.initialize();
                } catch (Exception exception) {
                    throw new ProcessorException("Error instanciating processor", exception);
                }
            }
        }
        return processor;
    }

    /**
     * Process a command in this context
     * @param command command to process
     * @param <R> casted response
     * @return R response
     */
    public <R> R processCommand(Command command) {
        Class<? extends Processor> processorClass = processorsByCommand.get(command.getClass());
        if (processorClass == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return (R) getProcessorInstance(processorClass).process(command);
    }

    /**
     * Add a new view factory to the context
     * @param viewFactoryName name of the view factory
     * @param viewFactory view factory
     */
    public void addViewFactory(String viewFactoryName, ViewFactory viewFactory) {
        viewFactories.put(viewFactoryName, viewFactory);
    }

    /**
     * Remove a view factory from the context
     * @param viewFactoryName name of the view factory
     */
    public void removeViewFactory(String viewFactoryName) {
        viewFactories.remove(viewFactoryName);
    }

    /**
     * Creates a view from a view name
     * @param viewName name of the view
     * @return View created view
     * @throws ViewException
     */
    public View createView(String viewName) throws ViewException {

        String viewFactoryName = null;
        if (viewFactories.size() == 1) {
            viewFactoryName = viewFactories.keySet().iterator().next();
        }
        else if (hasProperty(DEFAULT_VIEW_FACTORY_PROPERTY_NAME)) {
            viewFactoryName = (String)getProperty(DEFAULT_VIEW_FACTORY_PROPERTY_NAME);
        }
        return createView(viewFactoryName, viewName);
    }

    /**
     * Creates a view from a view name
     * @param viewFactoryName name of a view factory
     * @param viewName name of the view
     * @return View created view
     * @throws ViewException
     */
    public View createView(String viewFactoryName, String viewName) throws ViewException {

        View view = null;
        ViewFactory viewFactory = viewFactories.get(viewFactoryName);
        if (viewFactory != null) {
            view = viewFactory.createView(viewName);
        }
        if (view == null) {
            throw new ViewNotFoundException(MessageFormat.format("View \"" + viewName + " not found !!", viewName));
        }
        return view;
    }

    /**
     * Adds a new data source
     * @param dataSourceName name of the data source
     * @param dataSource data source
     */
    public void addDataSource (String dataSourceName, DataSource dataSource) {
        this.dataSources.put(dataSourceName, dataSource);
    }

    /**
     * Removes a data source
     * @param dataSourceName name of the data source
     */
    public void removeDataSource (String dataSourceName) {
        this.dataSources.remove(dataSourceName);
    }

    /**
     * Get the default data source.
     * @return DataSource Data source
     */
    public DataSource getDataSource () {
        String dataSourceName = null;
        if (dataSources.size() == 1) {
            dataSourceName = dataSources.keySet().iterator().next();
        }
        else if (hasProperty(DEFAULT_DATA_SOURCE_PROPERTY_NAME)) {
            dataSourceName = (String)getProperty(DEFAULT_DATA_SOURCE_PROPERTY_NAME);
        }
        return getDataSource(dataSourceName);
    }

    /**
     * Get a data source
     * @param dataSourceName name of the data source
     * @return DataSource Data source
     */
    public DataSource getDataSource (String dataSourceName) {
        return dataSources.get(dataSourceName);
    }

    /**
     * Start the registered processors
     */
    private void startProcessors () {
        for (Class<? extends Processor> processorClass : registeredProcessors) {
            ProcessorCommands processorAnnotation = processorClass.getAnnotation(ProcessorCommands.class);
            if (processorAnnotation != null) {
                Class<? extends Command>[] commandClasses = processorAnnotation.value();
                for (Class<? extends Command> commandClass : commandClasses) {
                    processorsByCommand.put(commandClass, processorClass);
                }
            }

            MultiInstanceProcessor multiInstanceProcessorAnnotation = processorClass.getAnnotation(MultiInstanceProcessor.class);
            if (multiInstanceProcessorAnnotation == null) {
                try {
                    Processor processor = processorClass.newInstance();
                    processor.setApplicationContext(this);
                    processor.initialize();
                    singleInstanceProcessors.put(processorClass, processor);
                } catch (Exception exception) {
                    throw new ProcessorException("Error instanciating processor", exception);
                }
            }
        }
    }

    /**
     * Stops the registered processors
     */
    private void stopProcessors () {
        singleInstanceProcessors.clear();
        processorsByCommand.clear();
    }

    /**
     * Start the context
     */
    public final void start () {
        if (!running) {
            startProcessors();
            onStart();
            running = true;
        }
    }

    /**
     * Stops the context
     */
    public final void stop () {
        if (running) {
            stopProcessors();
            onStop();
            running = false;
        }
    }

    /**
     * Method that is executed when the context starts
     */
    protected abstract void onStart ();

    /**
     * Method that is executed when the context stops
     */
    protected abstract void onStop ();
}
