
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorComponent;
import org.neogroup.sparks.processors.ProcessorException;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
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

    public Logger getLogger() {
        return logger;
    }

    public Properties getProperties() {
        return properties;
    }

    public <R> R getProperty(String property) {
        return (R)this.properties.get(property);
    }

    public boolean hasProperty(String property) {
        return this.properties.containsKey(property);
    }

    public void setProperty(String property, Object value) {
        this.properties.put(property, value);
    }

    public void loadPropertiesFromResource (String resourceName) throws IOException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            this.properties.load(in);
        }
    }

    public void loadPropertiesFromFile (String filename) throws IOException {
        try (FileInputStream in = new FileInputStream(filename)) {
            this.properties.load(in);
        }
    }

    public String getString (Locale locale, String key, Object... args) {
        String value = null;
        if (hasProperty(DEFAULT_BUNDLE_NAME_PROPERTY_NAME)) {
            value = getBundleString(getProperty(DEFAULT_BUNDLE_NAME_PROPERTY_NAME), locale, key, args);
        }
        return value;
    }

    public String getBundleString (String bundleName, Locale locale, String key, Object... args) {
        String value = null;
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        if (bundle != null && bundle.containsKey(key)) {
            value = MessageFormat.format(bundle.getString(key), args);
        }
        return value;
    }

    public final void registerProcessors (Class<? extends Processor> ... processorClasses) {
        for (Class<? extends Processor> processorClass : processorClasses) {
            registerProcessor(processorClass);
        }
    }

    public final void registerProcessor (Class<? extends Processor> processorClass) {
        registeredProcessors.add(processorClass);
    }

    public Set<Class<? extends Processor>> getRegisteredProcessors() {
        return registeredProcessors;
    }

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

    public <R> R processCommand(Command command) {
        Class<? extends Processor> processorClass = processorsByCommand.get(command.getClass());
        if (processorClass == null) {
            throw new ProcessorNotFoundException("Processor not found for command \"" + command.toString() + "\" !!");
        }
        return (R) getProcessorInstance(processorClass).process(command);
    }

    public void addViewFactory(String viewFactoryName, ViewFactory viewFactory) {
        viewFactories.put(viewFactoryName, viewFactory);
    }

    public void removeViewFactory(String viewFactoryName) {
        viewFactories.remove(viewFactoryName);
    }

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

    public void addDataSource (String dataSourceName, DataSource dataSource) {
        this.dataSources.put(dataSourceName, dataSource);
    }

    public void removeDataSource (String dataSourceName) {
        this.dataSources.remove(dataSourceName);
    }

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

    public DataSource getDataSource (String dataSourceName) {
        return dataSources.get(dataSourceName);
    }

    private void startProcessors () {
        for (Class<? extends Processor> processorClass : registeredProcessors) {
            ProcessorComponent processorAnnotation = processorClass.getAnnotation(ProcessorComponent.class);
            if (processorAnnotation != null) {

                if (processorAnnotation.singleInstance()) {
                    try {
                        Processor processor = processorClass.newInstance();
                        processor.setApplicationContext(this);
                        processor.initialize();
                        singleInstanceProcessors.put(processorClass, processor);
                    } catch (Exception exception) {
                        throw new ProcessorException("Error instanciating processor", exception);
                    }
                }

                Class<? extends Command>[] commandClasses = processorAnnotation.commands();
                for (Class<? extends Command> commandClass : commandClasses) {
                    processorsByCommand.put(commandClass, processorClass);
                }
            }
        }
    }

    private void stopProcessors () {
        singleInstanceProcessors.clear();
        processorsByCommand.clear();
    }

    public final void start () {
        if (!running) {
            startProcessors();
            onStart();
            running = true;
        }
    }

    public final void stop () {
        if (running) {
            stopProcessors();
            onStop();
            running = false;
        }
    }

    protected abstract void onStart ();
    protected abstract void onStop ();
}
