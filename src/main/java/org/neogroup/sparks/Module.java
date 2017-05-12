
package org.neogroup.sparks;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorNotFoundException;
import org.neogroup.sparks.views.View;
import org.neogroup.sparks.views.ViewException;
import org.neogroup.sparks.views.ViewNotFoundException;

import javax.sql.DataSource;

public abstract class Module extends ApplicationContext {

    protected final Application application;

    public Module(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    @Override
    public Object getProperty(String property) {
        Object value = super.getProperty(property);
        if (value == null) {
            value = application.getProperty(property);
        }
        return value;
    }

    @Override
    public boolean hasProperty(String property) {
        boolean hasProperty = super.hasProperty(property);
        if (!hasProperty) {
            hasProperty = application.hasProperty(property);
        }
        return hasProperty;
    }

    @Override
    public Processor getProcessorInstance(Class<? extends Processor> processorClass) {
        Processor processor = super.getProcessorInstance(processorClass);
        if (processor == null) {
            processor = application.getProcessorInstance(processorClass);
        }
        return processor;
    }

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

    @Override
    public DataSource getDataSource(String dataSourceName) {
        DataSource dataSource = super.getDataSource(dataSourceName);
        if (dataSource == null) {
            dataSource = application.getDataSource(dataSourceName);
        }
        return dataSource;
    }
}
